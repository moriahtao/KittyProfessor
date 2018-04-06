package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.StudentCourse;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UpdateStudentCourseResultTest {

    @Test
    public void setStudentCourse() {
        UpdateStudentCourseResult updateStudentCourseResult = new UpdateStudentCourseResult();
        StudentCourse sc = new StudentCourse();
        UpdateStudentCourseResult updateStudentCourseResult1 = new UpdateStudentCourseResult(sc);
        ArrayList<String> errors = new ArrayList<String>();
        errors.add("file too big");
        errors.add("wrong file format");
        UpdateStudentCourseResult updateStudentCourseResult2 = new UpdateStudentCourseResult(errors);
        updateStudentCourseResult1.setSuccess(true);
        updateStudentCourseResult1.setStudentCourse(sc);
        updateStudentCourseResult1.setErrorMessages(errors);
        assertEquals(errors,updateStudentCourseResult1.getErrorMessages());
        assertEquals(true,updateStudentCourseResult1.isSuccess());
        assertEquals(sc,updateStudentCourseResult1.getStudentCourse());
    }

}