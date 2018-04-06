package com.cs5500.team209.service;

import com.cs5500.team209.Driver;
import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.Course;
import com.cs5500.team209.model.StudentCourse;
import com.cs5500.team209.model.dto.UpdateAssignmentResult;
import com.cs5500.team209.repository.AssignmentRepository;
import com.cs5500.team209.repository.StudentCourseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Driver.class)
public class StudentCourseServiceTest {

    @TestConfiguration
    static class StudentCourseServiceTestContextConfiguration {
        @Bean
        public StudentCourseService studentCourseService() {
            return new StudentCourseService();
        }
    }

    @Autowired
    private StudentCourseService studentCourseService;

    @MockBean
    private StudentCourseRepository studentCourseRepository;

    @Test
    public void createStudentCourse() {
        Course course =  new Course();
        course.setTerm("fall2018");
        course.setCourseId("CS5500");
        course.setName("structural patterns");
        StudentCourse sc = new StudentCourse();
        studentCourseRepository.save(sc);
        Mockito.when(studentCourseRepository.save(Mockito.any(StudentCourse.class))).thenReturn(sc);
        studentCourseService.createStudentCourse(sc);
        studentCourseService.getCourseByCourseId(course.getCourseId());
        studentCourseService.deleteStudentCourse(course.getCourseId());
        studentCourseService.getAllCourses("username1");
    }

}