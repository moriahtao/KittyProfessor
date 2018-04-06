package com.cs5500.team209.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SubmissionTest {

    /**
     * This is to test the Submission class
     * The test checks if Submission are getting created correctly and
     * adding the required data*/
    @Test
    public void submissionTest() {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("results1");
        arr.add("results2");
        Submission submission = new Submission();
        Submission submission1 = new Submission("assignment1","username1",arr);
        Submission submission2 = new Submission("assignment1","username1",2);
        submission.setFilePaths(arr);
        submission.setUsername("username1");
        submission.setAssignmentId("Assignment1");
        submission.setSubmissionId("submission1");
        submission.setSubmissionNum(3);
        assertEquals("submissionId: submission1assignmentIdAssignment1usernameusername1submissions3",submission.toString());
        assertEquals("username1",submission.getUsername());
        assertEquals("Assignment1",submission.getAssignmentId());
        assertEquals("submission1",submission.getSubmissionId());
        assertEquals(3,submission.getSubmissionNum());
        assertEquals(arr.get(0),submission.getFilePaths().get(0));
    }
}