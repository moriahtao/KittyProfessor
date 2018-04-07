package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.StudentCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 3/18/18.
 *
 * the updated studentCourse obj
 */
public class UpdateStudentCourseResult {
    private boolean success;

    private StudentCourse studentCourse;

    private List<String> errorMessages;

    /**
     * default constructor
     */
    public UpdateStudentCourseResult() {
    }

    /**
     * encapsulate the studentCourse inside updateUserResult
     * @param studentCourse the studentCourse updated
     */
    public UpdateStudentCourseResult(StudentCourse studentCourse) {
        if (studentCourse != null) {
            this.success = true;
        } else {
            this.success = false;
            this.errorMessages = new ArrayList<>();
            errorMessages.add("Error.");
        }

        this.studentCourse = studentCourse;
    }

    /**
     * encapsulate the update failure msg
     * @param errorMessages the update studentCourse error msg
     */
    public UpdateStudentCourseResult(List<String> errorMessages) {
        this.success = false;
        this.errorMessages = errorMessages;
    }

    /**
     * if the update is successful
     * @return true if successfully updated
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * set if the update is successful or not
     * @param success true if successful
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * get studentCourse updated
     * @return the updated user
     */
    public StudentCourse getStudentCourse() {
        return studentCourse;
    }

    /**
     * set studentCourse
     * @param studentCourse set the updated studentCourse
     */
    public void setStudentCourse(StudentCourse studentCourse) {
        this.studentCourse = studentCourse;
    }

    /**
     * get the update error msg
     * @return the list of error msg
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * set the update error msg
     * @param errorMessages the list of error msg
     */
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
