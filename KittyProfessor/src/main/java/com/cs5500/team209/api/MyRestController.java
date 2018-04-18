package com.cs5500.team209.api;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.cs5500.team209.WebUtils;
import com.cs5500.team209.controller.SubmissionController;
import com.cs5500.team209.model.Course;
import com.cs5500.team209.repository.UserRepository;
import com.cs5500.team209.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by mengtao on 2/20/18.
 *
 * sample apis for validating other RESTful APIs
 */
@RestController
public class MyRestController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CourseService courseService;
    /**
     * the sample api
     * @param name the name sent from client
     * @return the name and ok status
     */
    @RequestMapping(method=GET, path="/greeting")
    @Secured("Role_Admin")
    public Object greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return WebUtils.successMap(name);
    }

    /**
     * api to get current user in session
     * @param request the request to get current user
     * @return the current username
     */
    @RequestMapping(method=GET, path="/currentuser")
    public Object getCurrentUser(HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        return WebUtils.successMap(name);
    }

    @RequestMapping(method=GET, path="/getRelatedCourses")
    public String getRelatedCourses(@RequestParam("courseCode") String courseCode,
                                    @RequestParam("courseId") String courseId) {

        List<Course> courses = courseService.findCourseByCriteria(courseCode, courseId);
        StringBuffer str = new StringBuffer("");
        for (Course course: courses) {
             str.append("<input type=\"checkbox\" name=\""+course.getCourseId()+"\" class=\"similar-courses\"/> " +
                     "<span><b>"+course.getName()+"</b> by <b>"+course.getUserName()+"</b> during <b>" +
                     course.getTerm() +
                     "</b></span><br>");
        }
        return str.toString();
    }


    @PostMapping("sendEmailStudent")
    public void sendEmailStudent(@RequestParam("email1") String email1,
                                   @RequestParam("email2") String email2,
                                   @RequestParam("message") String message,
                                   @RequestParam("filePath") String filePath) {
        sendEmail(email1,email2, message, filePath);
    }

    private void sendEmail(String email2,String email1, String message, String link) {

        String html_body = "<html>"
                + "<head></head>"
                + "<body>"
                + "<h3>"+message+"</h3>"
                + "<a href="+link+">"+link+"</a>"+
                "</body>"
                + "</html>";


        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        .withRegion(Regions.US_EAST_1).build();
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(email1)
                                .withCcAddresses(email2))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8")
                                .withData(html_body.toString())))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData("report for assignment")))
                .withSource("busted@kittyprofessor.com");

        client.sendEmail(request);
    }

}
