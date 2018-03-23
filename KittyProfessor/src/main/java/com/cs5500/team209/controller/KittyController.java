package com.cs5500.team209.controller;

import com.cs5500.team209.WebUtils;
import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.Course;
import com.cs5500.team209.model.Login;
import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.FetchUserResult;
import com.cs5500.team209.model.dto.UpdateUserResult;
import com.cs5500.team209.service.AssignmentService;
import com.cs5500.team209.service.CourseService;
import com.cs5500.team209.service.UserService;
import com.cs5500.team209.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Scope("session")
public class KittyController {

    @Autowired
    CourseService courseService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    UserService userService;

    @Autowired
    StorageService storageService;


    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute(new Login());
        return "login";
    }

    @PostMapping("/courses")
    public String login(HttpServletRequest request,
                        @ModelAttribute Login login,
                        Model model) {
        FetchUserResult user = userService.getUserByUsername(login.getUserName());

        request.getSession().setAttribute("userName",
                login.getUserName());

        request.getSession().setAttribute("role",
                 user.getUser().getJoinAs());

        model.addAttribute("courses", courseService.getAllCourses(login.getUserName()));

        if(request.getSession().getAttribute("role").equals("student")) {
            List<Course> allCourses = courseService.getAllCourses();
            List<Course> allCourses1 = new ArrayList<>();
            for(Course course2: allCourses) {
                if(!course2.getId().contains("student")) {
                    allCourses1.add(course2);
                }
            }
            model.addAttribute("allCourses", allCourses1);
        }
        model.addAttribute("course", new Course());
        return "new-course";
    }

    @PostMapping("/addCoursesStudent")
    public String addCourseStudent(HttpServletRequest request,
                            @ModelAttribute Course course,
                            Model model) {

        Course course1 = courseService.getCourseByCourseId(course.getId());
        course1.setId(course1.getId()+"student");
        course1.setUserName((String) request.getAttribute("userName"));
        courseService.createCourse(course1);

        model.addAttribute("courses", courseService.getAllCourses(course.getUserName()));
        List<Course> allCourses = courseService.getAllCourses();
        List<Course> allCourses1 = new ArrayList<>();
        for(Course course2: allCourses) {
            if(!course2.getId().contains("student")) {
                allCourses1.add(course2);
            }
        }
        model.addAttribute("allCourses", allCourses1);
        model.addAttribute("course", new Course());
        return "new-course";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("newUser", new User());
        return "signup";
    }

    @PostMapping("/addcourses")
    public String addCourse(HttpServletRequest request,
            @ModelAttribute Course course,
                            Model model) {

        course.setUserName((String)request.getSession().getAttribute("userName"));
        course.setTerm("spring18");
        course.setId(course.getUserName()+course.getCourseCode()
                +course.getTerm());
        courseService.createCourse(course);
        model.addAttribute("courses", courseService.getAllCourses(course.getUserName()));
        model.addAttribute("course", new Course());
        return "new-course";
    }

    @GetMapping("/assignments")
    public String assignmentsPage(@RequestParam("courseId")  String courseId,
                                  Model model) {
        String newCourseId = courseId.replace("student", "");
        List<Assignment> assignments = assignmentService.getAssignmentsForCourse(newCourseId);
        model.addAttribute("assignments", assignments);
        Assignment assignment = new Assignment();
        assignment.setCourseID(newCourseId);
        model.addAttribute("assignment", assignment);
        //model.addAttribute("cId", newCourseId);

        return "new-assignment-prof";
    }

    @PostMapping("/addAssignments")
    public String addAssignmentsPage(@ModelAttribute Assignment assignment,
                                  Model model) {
        System.out.println(assignment.getCourseID());
        assignment.setAssignmentId(assignment.getCourseID() +
                assignment.getName().replaceAll("\\s+",""));

        System.out.println(assignment.getCourseID());
        System.out.println(assignment.getAssignmentId());
        System.out.println(assignment.getName());
        System.out.println(assignment.getDue());
        System.out.println(assignment.getThreshold());

        assignmentService.createAssignment(assignment);
        List<Assignment> assignments = assignmentService.getAssignmentsForCourse(assignment.getCourseID());

        System.out.println(assignments.size());
        model.addAttribute("assignments", assignments);
        model.addAttribute("assignment", new Assignment());
        model.addAttribute("cId", assignment.getCourseID());
        return "new-assignment-prof";
    }

    @PostMapping("/upload")
    public String handleFileUpload(HttpServletRequest request,
                                   @RequestParam("file") MultipartFile file) {

        String userName = (String)request.getSession().getAttribute("userName");
        try {
            Files.createDirectories(Paths.get("exercise1/"+userName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        storageService.store(file, Paths.get("exercise1/"+userName));
        return "redirect:/";
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute User user, Model model) {
        userService.createUser(user);
        model.addAttribute(new Login());
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "login";
    }

}
