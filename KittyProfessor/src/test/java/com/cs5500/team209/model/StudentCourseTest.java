package com.cs5500.team209.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class StudentCourseTest {

    @Test
    public void teststudentcourse() {
        StudentCourse sc = new StudentCourse();
        sc.setUserName("username1");
        sc.setStudentCourseId("CS5500");
        sc.setCourseId("200");
        assertEquals("username1",sc.getUserName());
        assertEquals("CS5500",sc.getStudentCourseId());
        assertEquals("200",sc.getCourseId());
    }
}