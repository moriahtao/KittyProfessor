package com.cs5500.team209.service;

import com.cs5500.team209.Driver;
import com.cs5500.team209.model.Course;
import com.cs5500.team209.model.Role;
import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.FetchUserResult;
import com.cs5500.team209.model.dto.UpdateCourseResult;
import com.cs5500.team209.model.dto.UpdateUserResult;
import com.cs5500.team209.repository.CourseRepository;
import com.cs5500.team209.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Driver.class)
public class CourseServiceTest {

    @TestConfiguration
    static class CourseServiceTestContextConfiguration {

        @Bean
        public CourseService courseService() {
            return new CourseService();
        }
    }

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;


    /**
     * setup mock db before testing
     */
    @Before
    public void setUp() {
        Course msd = new Course();
        msd.setCourseCode("2");
        courseRepository.save(msd);
        Mockito.when(courseRepository.findCourseByCourseId(msd.getCourseId())).thenReturn(msd);
        Mockito.when(courseRepository.findCourseByCourseId("5")).thenReturn(null);
        Mockito.when(courseRepository.save(Mockito.any(Course.class))).thenReturn(msd);

    }

    @Test
    public void whenValidName_thenCourseShouldBeFound() {
        String courseId = "200";
        Course found = courseService.getCourseByCourseId(courseId);
    }

    @Test
    public void whenValidUser_thenCourseShouldBeFound() {
        Course msd1 = new Course();
        msd1.setCourseCode("CS5500");
        msd1.setCourseCode("2");
        msd1.setTerm("Spring 2018");
        msd1.setNumStudents(70);
        Mockito.when(courseRepository.save(Mockito.any(Course.class))).thenReturn(msd1);
        UpdateCourseResult found = courseService.createCourse(msd1);
        System.out.println(courseService.getCourseByCourseId("2"));
        courseService.deleteCourse(msd1);

    }

}