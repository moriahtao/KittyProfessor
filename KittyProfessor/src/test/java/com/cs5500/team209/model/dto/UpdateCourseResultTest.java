package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Course;
import com.cs5500.team209.model.Report;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UpdateCourseResultTest {

    @Test
    public void getCourse() {
        UpdateCourseResult updateCourseResult = new UpdateCourseResult();
        Course course = new Course();
        UpdateCourseResult updateCourseResult1 = new UpdateCourseResult(course);
        ArrayList<String> errors = new ArrayList<String>();
        errors.add("file too big");
        errors.add("wrong file format");
        UpdateCourseResult updateCourseResult2 = new UpdateCourseResult(errors);
        updateCourseResult1.setCourse(course);
        updateCourseResult1.setSuccess(true);
        updateCourseResult1.setErrorMessages(errors);
        assertEquals(errors.get(0),updateCourseResult1.getErrorMessages().get(0));
        assertEquals(course,updateCourseResult1.getCourse());
        assertEquals(true,updateCourseResult1.isSuccess());
    }
}