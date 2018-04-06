package com.cs5500.team209.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest {

    /**
     * This is to test the Course class
     * The test checks if courses are getting created correctly and
     * adding the required data*/
    @Test
    public void getCourseId() {
        Course course = new Course();
        course.setName("Web dev");
        course.setUserName("Alice");
        course.setCourseId("2");
        course.setCourseCode("CS5500");
        course.setNumAssignments(75);
        course.setNumStudents(50);
        course.setTerm("Spring 2018");
        assertEquals("Web dev",course.getName());
        assertEquals("Alice",course.getUserName());
        assertEquals("CS5500",course.getCourseCode());
        assertEquals(75,course.getNumAssignments());
        assertEquals("2",course.getCourseId());
        assertEquals("Spring 2018",course.getTerm());
        assertEquals(50,course.getNumStudents());
        assertEquals("Spring 2018",course.getTerm());
    }
}