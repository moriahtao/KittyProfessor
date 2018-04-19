package com.cs5500.team209.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.cs5500.team209.model.Course;
import com.cs5500.team209.repository.UserRepository;
import com.cs5500.team209.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class KittyRestController {
    @Autowired
    private UserRepository repository;

    @Autowired
    private CourseService courseService;

    /**
     *
     * @param courseCode
     * @param courseId
     * @return
     */
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

    /**
     *
     * @param email1
     * @param email2
     * @param message
     * @param filePath
     */
    @PostMapping("sendEmailStudent")
    public void sendEmailStudent(@RequestParam("email1") String email1,
                                 @RequestParam("email2") String email2,
                                 @RequestParam("message") String message,
                                 @RequestParam("filePath") String filePath) {
        sendEmail(email1,email2, message, filePath);
    }

    @PostMapping("sendFeedback")
    public void sendFeedBack(@RequestParam("feedback") String feedback) {
        sendMessage("feedback@kittyprofessor.com", feedback, "Feedback from user");
    }

    @PostMapping("contactKitty")
    public void sendQuery(@RequestParam("query") String query) {
        sendMessage("query@kittyprofessor.com", query, "Query from user");
    }

    private void sendMessage(String to, String message, String subject) {
        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        .withRegion(Regions.US_EAST_1).build();
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8")
                                .withData(message)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(subject)))
                .withSource("admin@kittyprofessor.com");

        client.sendEmail(request);
    }

    /**
     *
     * @param email2
     * @param email1
     * @param message
     * @param link
     */
    private void sendEmail(String email2,String email1, String message, String link) {

        String html_body = "<html>"
                + "<head></head>"
                + "<body>"
                + "<h3>"+message+"</h3>"
                + "<a href="+link+">Report</a>"+
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
