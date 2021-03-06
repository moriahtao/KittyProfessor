package com.cs5500.team209.controller;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.ListVerifiedEmailAddressesResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.cs5500.team209.model.*;
import com.cs5500.team209.model.dto.FetchUserResult;
import com.cs5500.team209.service.AssignmentService;
import com.cs5500.team209.service.CourseService;
import com.cs5500.team209.service.StudentCourseService;
import com.cs5500.team209.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import static sun.security.x509.X509CertInfo.SUBJECT;

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
    StudentCourseService studentCourseService;

    String[] terms = new String[]{"fall17", "spring18"};
    String currentSemester = "spring18";

    /**
     * Home page
     * @param model to send data ro front end
     * @return login html page
     */
    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute(new Login());
        return "login";
    }

    /**
     * admin page login
     * @param model model to send data to front end
     * @return admin login html page
     */
    @GetMapping("/admin")
    public String admin(Model model)  {
        model.addAttribute(new Login());
        return "adminlogin";
    }

    /**
     * Admin dashboard
     * @param request request for username
     * @param login login model sent from front end
     * @param model model to output data
     * @return admin dashboard page
     */
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
        for (User user : users) {
            if (!user.getJoinAs().equals("admin")) {
                users1.add(user);
            }
        }
        model.addAttribute("users", users1);
        model.addAttribute("editUser", new User());
        model.addAttribute("deleteUser", new User());
        return "adminDashboard";
    }

    /**
     * when user submits edit user option
     * @param editUser edit user details
     * @param model model to send data back
     * @return admin dashboard to display
     */
    @PostMapping("/editUser")
    public String editUser(@ModelAttribute User editUser,
                           Model model) {
        FetchUserResult userResult = userService.getUserByUsername(editUser.getUsername());
        User user = userResult.getUser();
        user.setJoinAs(editUser.getJoinAs());

        userService.createUser(user);
        userService.createUser(user);

        List<User> users = userService.getAllUsers();
        List<User> users1 = new ArrayList<>();
        for(User usert: users) {
            if(!usert.getJoinAs().equals("admin")) {
                users1.add(usert);
            }
        }
        courseService.deleteCourseByUserName(editUser.getUsername());
        assignmentService.deleteAssignmentByUserName(editUser.getUsername());

        model.addAttribute("users", users1);
        model.addAttribute("editUser", new User());
        model.addAttribute("deleteUser", new User());
        return "adminDashboard";
    }

    /**
     * Delete user on request from admin
     * @param deleteUser delete user details
     * @param model model to send back data
     * @return admin dashboard
     */
    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute User deleteUser,
                             Model model) {
        userService.deleteUser(deleteUser.getUsername());

        List<User> users = userService.getAllUsers();
        List<User> users1 = new ArrayList<>();
        for(User usert: users) {
            if(!usert.getJoinAs().equals("admin")) {
                users1.add(usert);
            }
        }
        courseService.deleteCourseByUserName(deleteUser.getUsername());
        assignmentService.deleteAssignmentByUserName(deleteUser.getUsername());

        model.addAttribute("users", users1);
        model.addAttribute("editUser", new User());
        model.addAttribute("deleteUser", new User());
        return "adminDashboard";
    }

    @PostMapping("/courses")
    public String login(HttpServletRequest request,
                        @ModelAttribute Login login,
                        Model model) {
        FetchUserResult user = userService.getUserByUsername(login.getUserName());
        try {
            if (user.getUser() == null ||
                    !user.getUser().getPassword().equals(login.getPassword())) {
                model.addAttribute(new Login());
                model.addAttribute("InvalidUser", true);
                return "login";
            }
        } catch(Exception e) {
            model.addAttribute(new Login());
            model.addAttribute("InvalidUser", true);
            return "login";
        }
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
                .standard().withRegion("us-east-1").build();
        ListVerifiedEmailAddressesResult result = client.listVerifiedEmailAddresses();
        if(!result.getVerifiedEmailAddresses().contains(user.getUser().getEmail())) {
            model.addAttribute(new Login());
            model.addAttribute("InvalidEmail", true);
            return "login";
        }
        request.getSession().setAttribute("userName",
                login.getUserName());

        request.getSession().setAttribute("role",
                user.getUser().getJoinAs());

        request.getSession().setAttribute("terms", terms);

        request.getSession().setAttribute("currentTerm", currentSemester);

        if(user.getUser().getJoinAs().equals("student")) {

            List<StudentCourse> allScs = studentCourseService
                    .getAllCourses(login.getUserName());

            model.addAttribute("courses", getCourses(allScs));
            model.addAttribute("studentCourse", new StudentCourse());
            model.addAttribute("leftCourses", getLeftCourses(allScs));
            model.addAttribute("deleteStudentCourse", new StudentCourse());
            return "course";
        }

        model.addAttribute("courses", courseService.getAllCourses(login.getUserName()));
        model.addAttribute("course", new Course());
        model.addAttribute("editCourse", new Course());
        model.addAttribute("deleteCourse", new Course());
        return "course";
    }

    @GetMapping("/goCourses")
    public String goCourses(HttpServletRequest request,
                            Model model) {

        FetchUserResult user = userService.getUserByUsername(
                (String) request.getSession().getAttribute("userName"));

        if(user.getUser().getJoinAs().equals("student")) {

            List<StudentCourse> allScs = studentCourseService
                    .getAllCourses(user.getUser().getUsername());

            model.addAttribute("courses", getCourses(allScs));
            model.addAttribute("studentCourse", new StudentCourse());
            model.addAttribute("leftCourses", getLeftCourses(allScs));
            model.addAttribute("deleteStudentCourse", new StudentCourse());
            return "course";
        }

        model.addAttribute("courses", courseService.getAllCourses(user.getUser().getUsername()));
        model.addAttribute("course", new Course());
        model.addAttribute("editCourse", new Course());
        model.addAttribute("deleteCourse", new Course());
        return "course";
    }

    private List<Course> getLeftCourses(List<StudentCourse> allScs) {
        List<Course> allCoursest = courseService.getAllCourses();
        List<Course> allCourses = new ArrayList<>();
        Set<String> courseIds = new HashSet<>();
        for(StudentCourse sc : allScs) {
            courseIds.add(sc.getCourseId());
        }

        for(Course course : allCoursest) {
            if(courseIds.contains(course.getCourseId()) &&
                    !course.getTerm().equals(currentSemester))
                continue;
            allCourses.add(course);
        }
        return allCourses;
    }

    /**
     * Returns list of courses
     * @param allScs student course info
     * @return returns list of courses
     */
    private List<Course> getCourses(List<StudentCourse> allScs) {
        List<Course> allCourses = new ArrayList<>();
        for(StudentCourse sc : allScs) {
            allCourses.add(courseService.getCourseByCourseId(sc.getCourseId()));
        }
        return allCourses;
    }

    /**
     * edit course
     * @param course
     * @param model
     * @return
     */
    @PostMapping("/editCourse")
    public String editCourse(HttpServletRequest request,
                             @ModelAttribute Course course,
                             Model model) {
        course.setUserName((String)request.getSession().getAttribute("userName"));
        course.setTerm(currentSemester);
        courseService.createCourse(course);
        model.addAttribute("courses", courseService.getAllCourses(course.getUserName()));
        model.addAttribute("course", new Course());
        model.addAttribute("editCourse", new Course());
        model.addAttribute("deleteCourse", new Course());
        return "course";
    }

    @PostMapping("/deleteCourse")
    public String deleteCourse( HttpServletRequest request,
                              @ModelAttribute Course deleteCourse,
                              Model model) {
        courseService.deleteCourse(deleteCourse.getCourseId());
        model.addAttribute("courses", courseService.getAllCourses(
                (String) request.getSession().getAttribute("userName")));
        model.addAttribute("course", new Course());
        model.addAttribute("editCourse", new Course());
        model.addAttribute("deleteCourse", new Course());
        return "course";
    }

    @PostMapping("/deleteStudentCourse")
    public String deleteCourseStudent( HttpServletRequest request,
                                @ModelAttribute StudentCourse studentCourse,
                                Model model) {

        studentCourse.setUserName((String) request.getSession().getAttribute("userName"));
        studentCourseService.deleteStudentCourse(studentCourse.getCourseId() +
                studentCourse.getUserName());

        Course course = courseService.getCourseByCourseId(studentCourse.getCourseId());
        course.setNumStudents(course.getNumStudents() - 1);
        courseService.createCourse(course);

        List<StudentCourse> allScs = studentCourseService
                .getAllCourses((String) request.getSession().getAttribute("userName"));
        model.addAttribute("courses", getCourses(allScs));
        model.addAttribute("leftCourses", getLeftCourses(allScs));
        model.addAttribute("studentCourse", new StudentCourse());
        model.addAttribute("deleteStudentCourse", new StudentCourse());
        return "course";
    }

    @PostMapping("/addCoursesStudent")
    public String addCourseStudent(HttpServletRequest request,
                            @ModelAttribute StudentCourse studentCourse,
                            Model model) {
        String userName = (String) request.getSession().getAttribute("userName");
        studentCourse.setUserName(userName);
        studentCourse.setStudentCourseId(studentCourse.getCourseId() + userName);
        studentCourseService.createStudentCourse(studentCourse);

        Course course = courseService.getCourseByCourseId(studentCourse.getCourseId());
        course.setNumStudents(course.getNumStudents() + 1);
        courseService.createCourse(course);

        List<StudentCourse> allScs = studentCourseService
                .getAllCourses((String) request.getSession().getAttribute("userName"));

        model.addAttribute("courses", getCourses(allScs));
        model.addAttribute("leftCourses", getLeftCourses(allScs));
        model.addAttribute("studentCourse", new StudentCourse());
        model.addAttribute("deleteStudentCourse", new StudentCourse());
        return "course";
    }



    @GetMapping("/signup")
    public String signup(Model model) {
        List<String> allUsers = new ArrayList<>();
        for(User user: userService.getAllUsers()) {
            allUsers.add(user.getUsername());
        }
        model.addAttribute("allUsers", String.join(",", allUsers));
        model.addAttribute("newUser", new User());
        return "signup";
    }

    @PostMapping("/addcourses")
    public String addCourse(HttpServletRequest request,
                            @ModelAttribute Course course,
                            Model model) {

        course.setUserName((String) request.getSession().getAttribute("userName"));
        course.setTerm(currentSemester);
        course.setNumAssignments(0);
        course.setNumStudents(0);
        course.setCourseId("course"+String.valueOf(System.currentTimeMillis() / 1000L));

        courseService.createCourse(course);
        model.addAttribute("courses", courseService.getAllCourses(course.getUserName()));
        model.addAttribute("course", new Course());
        model.addAttribute("editCourse", new Course());
        model.addAttribute("deleteCourse", new Course());
        return "course";
    }

    @GetMapping("/assignments")
    public String assignmentsPage(HttpServletRequest request,
                                  @RequestParam("courseId") String courseId,
                                  Model model) {
        String role = (String)request.getSession().getAttribute("role");
        List<Assignment> assignments = assignmentService.getAssignmentsForCourse(courseId);
        Course course1 = courseService.getCourseByCourseId(courseId);
        String courseName = course1.getName();
        String courseCode = course1.getCourseCode();
        model.addAttribute("assignments", assignments);
        model.addAttribute("courseName", courseName);
        model.addAttribute("courseCode", courseCode);
        if(role.equals("instructor")) {
            Assignment assignment = new Assignment();
            assignment.setCourseId(courseId);
            model.addAttribute("assignment", assignment);
            model.addAttribute("editAssignment", new Assignment());
            model.addAttribute("deleteAssignment", new Assignment());
        }
        return "assignment";
    }

    @PostMapping("/addAssignments")
    public String addAssignmentsPage(HttpServletRequest request,
                                    @ModelAttribute Assignment assignment,
                                    Model model) {

        String role = (String)request.getSession().getAttribute("role");
        assignment.setAssignmentId("assignment" +
                String.valueOf(System.currentTimeMillis() / 1000L));

        Course course = courseService.getCourseByCourseId(
                assignment.getCourseId());
        course.setNumAssignments(course.getNumAssignments() + 1);
        courseService.createCourse(course);

        assignmentService.createAssignment(assignment);
        List<Assignment> assignments = assignmentService.getAssignmentsForCourse(assignment.getCourseId());
        model.addAttribute("assignments", assignments);

        if(role.equals("instructor")) {
            Assignment newAss = new Assignment();
            newAss.setCourseId(assignment.getCourseId());
            model.addAttribute("courseName", course.getName());
            model.addAttribute("courseCode", course.getCourseCode());
            model.addAttribute("assignment", newAss);
            model.addAttribute("editAssignment", new Assignment());
            model.addAttribute("deleteAssignment", new Assignment());
        }
        return "assignment";
    }

    @PostMapping("/editAssignment")
    public String updateAssignmentsPage(@ModelAttribute Assignment editAssignment,
                                     Model model) {

        assignmentService.createAssignment(editAssignment);
        List<Assignment> assignments = assignmentService.getAssignmentsForCourse(editAssignment.getCourseId());

        Assignment newAss = new Assignment();
        newAss.setCourseId(editAssignment.getCourseId());

        Course course = courseService.getCourseByCourseId(
                editAssignment.getCourseId());

        model.addAttribute("courseName", course.getName());
        model.addAttribute("courseCode", course.getCourseCode());

        model.addAttribute("assignment", newAss);
        model.addAttribute("assignments", assignments);
        model.addAttribute("editAssignment", new Assignment());
        model.addAttribute("deleteAssignment", new Assignment());
        return "assignment";
    }

    @PostMapping("/deleteAssignment")
    public String deleteAssignmentsPage(@ModelAttribute Assignment deleteAssignment,
                                        Model model) {

        assignmentService.deleteAssignment(deleteAssignment.getAssignmentId());
        List<Assignment> assignments = assignmentService.getAssignmentsForCourse(
                deleteAssignment.getCourseId());

        Assignment newAss = new Assignment();
        newAss.setCourseId(deleteAssignment.getCourseId());

        Course course = courseService.getCourseByCourseId(
                deleteAssignment.getCourseId());
        course.setNumAssignments(course.getNumAssignments() -1);
        courseService.createCourse(course);

        model.addAttribute("courseName", course.getName());
        model.addAttribute("courseCode", course.getCourseCode());
        model.addAttribute("assignment", newAss);
        model.addAttribute("assignments", assignments);
        model.addAttribute("editAssignment", new Assignment());
        model.addAttribute("deleteAssignment", new Assignment());
        return "assignment";
    }


    @PostMapping("/adduser")
    public String addUser(@ModelAttribute User user, Model model) {
        userService.createUser(user);
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
                .standard().withRegion("us-east-1").build();
        VerifyEmailIdentityRequest request = new VerifyEmailIdentityRequest()
                .withEmailAddress(user.getEmail());
        client.verifyEmailIdentity(request);
        model.addAttribute(new Login());
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "login";
    }
}
