package com.cs5500.team209.service;

import com.cs5500.team209.model.Course;
import com.cs5500.team209.model.dto.UpdateCourseResult;
import com.cs5500.team209.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 3/18/18.
 *
 * Course related Service
 */
@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public UpdateCourseResult createCourse(Course course) {
        List<String> errorMessages = validateCourse(course);
        if (errorMessages == null || errorMessages.isEmpty()) {
            Course createdCourse = courseRepository.save(course);
            return new UpdateCourseResult(createdCourse);
        } else {
            return new UpdateCourseResult(errorMessages);
        }
    }

    /**
     * delete course
     * @param courseId the id of the course to be deleted
     */
    public void deleteCourse(String courseId) {
        courseRepository.deleteById(courseId);
    }

    public void deleteCourseByUserName(String userName) {
        courseRepository.deleteCourseByUserName(userName);
    }

    /**
     * get course by id
     * @param id the id of the course to be got
     * @return course
     */
    public Course getCourseByCourseId(String id) {
        return courseRepository.findCourseByCourseId(id);
    }

    /**
     * find all courses
     * @return a list of courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     *
     * @return
     */
    public List<Course> getAllCourses(String userName) {
        return courseRepository.findCourseByUserName(userName);
    }
    /**
     * validate course having the required fields
     * @param course the user to be validated
     * @return the error messages if any
     */
    private List<String> validateCourse(Course course) {
        List<String> errorMessages = new ArrayList<>();
        checkNull(errorMessages, course.getCourseId(), "Course Id");
        checkNull(errorMessages, course.getName(), "Course Name");
        return errorMessages;
    }

    /**
     * check if fields are null
     * @param errorMessages error msg
     * @param value the value to be checked
     * @param fieldName the field to be checked
     */
    private void checkNull(List<String> errorMessages, String value, String fieldName) {
        if (StringUtils.isEmpty(value)) {
            errorMessages.add(fieldName + " can't be null.");
        }
    }
}
