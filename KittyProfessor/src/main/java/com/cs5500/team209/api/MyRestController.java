package com.cs5500.team209.api;

import com.cs5500.team209.WebUtils;
import com.cs5500.team209.model.Course;
import com.cs5500.team209.repository.UserRepository;
import com.cs5500.team209.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
             str.append("<input type=\"checkbox\" nam=\"+course.getCourseId()+\"/> " +
                     "<span><b>"+course.getName()+"</b> by <b>"+course.getUserName()+"</b> during <b>" +
                     course.getTerm() +
                     "</b></span><br>");
        }
        return str.toString();
    }

}
