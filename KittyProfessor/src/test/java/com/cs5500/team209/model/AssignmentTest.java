package com.cs5500.team209.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AssignmentTest {
    /**
     * This is to test the Assignment class
     * The test checks if assignments are getting created correctly and
     * adding the required data*/
    @Test
    public void testAssignments(){
        Assignment assignment = new Assignment();
        assignment.setName("git practice");
        assignment.setAssignmentId("Assignment 1");
        assignment.setCourseID("CS5500");
        assignment.setThreshold(75);
        assignment.setDue("12-04-29");
        assignment.setDesc("Run and learn how to user git commands");
        assertEquals("git practice",assignment.getName());
        assertEquals("Assignment 1",assignment.getAssignmentId());
        assertEquals("CS5500",assignment.getCourseID());
        assertEquals(75,assignment.getThreshold());
        assertEquals("12-04-29",assignment.getDue());
        assertEquals("Run and learn how to user git commands",assignment.getDesc());
    }


}