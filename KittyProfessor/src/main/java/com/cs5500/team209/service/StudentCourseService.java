package com.cs5500.team209.service;


import com.cs5500.team209.model.StudentCourse;
import com.cs5500.team209.model.dto.UpdateStudentCourseResult;
import com.cs5500.team209.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mengtao on 3/18/18.
 *
 * Course related Service
 */
@Service
public class StudentCourseService {
    @Autowired
    StudentCourseRepository studentCourseRepository;

    public void createStudentCourse(StudentCourse studentcourse) {
            StudentCourse sc = studentCourseRepository.save(studentcourse);
    }

    /**
     * delete course
     * @param studentCourse the id of the course to be deleted
     */
    public void deleteStudentCourse(String studentCourseId) {
        studentCourseRepository.deleteById(studentCourseId);
    }

    /**
     * get course by id
     * @param id the id of the course to be got
     * @return course
     */
    public List<StudentCourse> getCourseByCourseId(String id) {
        return studentCourseRepository.findStudentCourseByCourseId(id);
    }

    /**
     *
     * @return
     */
    public List<StudentCourse> getAllCourses(String userName) {
        return studentCourseRepository.findStudentCourseByUserName(userName);
    }
}
