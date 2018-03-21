package com.cs5500.team209.api;

import com.cs5500.team209.WebUtils;
import com.cs5500.team209.model.Course;
import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.FetchUserResult;
import com.cs5500.team209.model.dto.UpdateCourseResult;
import com.cs5500.team209.model.dto.UpdateUserResult;
import com.cs5500.team209.service.CourseService;
import com.cs5500.team209.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by mengtao on 3/19/18.
 *
 * class to implement Course rest controllers
 *
 * with User
 */
@RestController
public class CourseRestController {
    @Autowired
    CourseService courseService;

    /**
     * rest api for creating a course
     * @param course the Course object to be created
     * @return response with course and http status
     */
    @RequestMapping(method=POST, path="/api/courses")
    public Object createCourse(@RequestBody Course course) {
        UpdateCourseResult result = courseService.createCourse(course);
        if (result.isSuccess()) {
            return WebUtils.createSuccessMap(result.getCourse());
        } else {
            return WebUtils.failedMap(result.getErrorMessages());
        }
    }

    /**
     * rest api for get course by id
     * @param id the courseId for query
     * @return response with course and http status
     */
    @RequestMapping(method=GET, path="/api/course/{id}")
    public Object getCourseById(@PathVariable("id") String id) {
        Course course = courseService.getCourseByCourseId(id);
        if (course != null)
            return WebUtils.successMap(course);
        else
            return WebUtils.failedMap("User not found.");
    }


    /**
     * rest api for get all courses
     * @return response with course and http status
     */
    @RequestMapping(method=GET, path="/api/course")
    public Object getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return WebUtils.successMap(courses);
    }

}
