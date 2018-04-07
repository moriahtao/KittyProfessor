package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Submission;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UpdateSubmissionResultTest {

    @Test
    public void getSubmission() {
        UpdateSubmissionResult updateSubmissionResult =new UpdateSubmissionResult();
        Submission submission = new Submission();
        UpdateSubmissionResult updateSubmissionResult1 =new UpdateSubmissionResult(submission);
        updateSubmissionResult1.setSubmission(submission);
        updateSubmissionResult1.setSuccess(true);
        ArrayList<String> errors = new ArrayList<String>();
        UpdateSubmissionResult updateSubmissionResult2 =new UpdateSubmissionResult(errors);
        errors.add("file too big");
        errors.add("wrong file format");
        updateSubmissionResult1.setErrorMessages(errors);
        assertEquals(submission,  updateSubmissionResult1.getSubmission());
        assertEquals(errors,  updateSubmissionResult1.getErrorMessages());
        assertEquals(true,  updateSubmissionResult1.isSuccess());

    }
}