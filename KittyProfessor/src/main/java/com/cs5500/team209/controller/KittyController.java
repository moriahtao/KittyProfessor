package com.cs5500.team209.controller;

import com.cs5500.team209.Parser;
import com.cs5500.team209.model.*;
import com.cs5500.team209.model.dto.FetchUserResult;
import com.cs5500.team209.model.dto.UpdateSubmissionResult;
import com.cs5500.team209.model.dto.UpdateUserResult;
import com.cs5500.team209.service.AssignmentService;
import com.cs5500.team209.service.CourseService;
import com.cs5500.team209.service.SubmissionService;
import com.cs5500.team209.service.UserService;
import com.cs5500.team209.storage.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@Scope("session")
public class KittyController {
    final static Logger logger = Logger.getLogger(KittyController.class);


    @Autowired
    CourseService courseService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    UserService userService;

    @Autowired
    SubmissionService submissionService;

    @Autowired
    StorageService storageService;


    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute(new Login());
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model) throws IOException, InterruptedException {
        model.addAttribute(new Login());
        return "adminlogin";
    }

    @PostMapping("/adminDashboard")
    public String adminDashboard(HttpServletRequest request,
                                 @ModelAttribute Login login,
                                 Model model) {
        request.getSession().setAttribute("userName",
                login.getUserName());

        request.getSession().setAttribute("role",
                "admin");
        List<User> users = userService.getAllUsers();
        List<User> users1 = new ArrayList<>();
        for(User user: users) {
            if(!user.getJoinAs().equals("admin")) {
                users1.add(user);
            }
        }
        model.addAttribute("users", users1);
        return "adminDashboard";
    }

    @GetMapping("/home")
    public String parse(Model model) throws IOException, InterruptedException {
        model.addAttribute(new Login());
        Parser.parse();
        TimeUnit.SECONDS.sleep(5);
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
                if(!course2.getCourseId().contains("student")) {
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

        Course course1 = courseService.getCourseByCourseId(course.getCourseId());
        course1.setCourseId(course1.getCourseId()+"student");
        course1.setUserName((String) request.getAttribute("userName"));
        courseService.createCourse(course1);

        model.addAttribute("courses", courseService.getAllCourses(course.getUserName()));
        List<Course> allCourses = courseService.getAllCourses();
        List<Course> allCourses1 = new ArrayList<>();
        for(Course course2: allCourses) {
            if(!course2.getCourseId().contains("student")) {
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
        course.setCourseId(course.getUserName()+course.getCourseCode()
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

        assignmentService.createAssignment(assignment);
        List<Assignment> assignments = assignmentService.getAssignmentsForCourse(assignment.getCourseID());

        model.addAttribute("assignments", assignments);
        model.addAttribute("assignment", new Assignment());
        model.addAttribute("cId", assignment.getCourseID());
        return "new-assignment-prof";
    }


    @GetMapping("/submissions")
    public String getSubmissionList(HttpServletRequest request, @RequestParam("assignmentId")  String assignmentId,
                                  Model model) {
        String username = request.getUserPrincipal().getName();
        List<Submission> submissionList = submissionService.getSubmissionsForAssignment(assignmentId);
        model.addAttribute("submissions", submissionList);
        Submission submission = new Submission(assignmentId, username);
        model.addAttribute("submission", submission);
        return "new-assignment-prof";
    }

    @PostMapping("/addSubmissions")
    public String createSubmissionForAssignment(@ModelAttribute Submission submission,
                                     Model model) {
        System.out.println(submission.getAssignmentId());
        submissionService.createSubmission(submission);
        List<Submission> submissionList = submissionService.getSubmissionsForAssignment(submission.getAssignmentId());

         model.addAttribute("submissions", submissionList);
        //model.addAttribute("submission", new Assignment());
        //model.addAttribute("cId", assignment.getCourseID());
        // TODO: add mapping page
        return "new-assignment-prof";
    }

    @PostMapping("/upload")
    public String handleFileUpload(HttpServletRequest request, @RequestParam("submissionId")  String submissionId,
                                   @RequestParam("file") MultipartFile file) {

        //String userName = (String)request.getSession().getAttribute("userName");
        try {
            Files.createDirectories(Paths.get("data/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Submission submission = submissionService.getSubmissionById(submissionId);
        if (submission != null) {
            UpdateSubmissionResult updateSubmissionResult =
                    submissionService.addFileToSubmission("data/" + file.getName() + "_" + submissionId, submission);
            if (updateSubmissionResult.isSuccess()) {
                storageService.store(file, Paths.get("data/"));
            } else {
                logger.warn("updateSubmission fail");
            }
        } else {
            logger.warn("fetch submission fail");
        }
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
