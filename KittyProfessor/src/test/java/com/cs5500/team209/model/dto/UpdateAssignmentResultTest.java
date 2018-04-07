package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.Course;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UpdateAssignmentResultTest {

    @Test
    public void getAssignment() {
        UpdateAssignmentResult updateAssignmentResult = new UpdateAssignmentResult();
        Assignment assignment = new Assignment();
        UpdateAssignmentResult updateAssignmentResult1 = new UpdateAssignmentResult(assignment);
        ArrayList<String> errors = new ArrayList<String>();
        errors.add("file too big");
        errors.add("wrong file format");
        UpdateAssignmentResult updateAssignmentResult12 = new UpdateAssignmentResult(errors);
        updateAssignmentResult1.setAssignment(assignment);
        updateAssignmentResult1.setSuccess(true);
        updateAssignmentResult1.setErrorMessages(errors);
        assertEquals(errors.get(0),updateAssignmentResult1.getErrorMessages().get(0));
        assertEquals(assignment,updateAssignmentResult1.getAssignment());
        assertEquals(true,updateAssignmentResult1.isSuccess());
    }
}