package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Course;
import com.cs5500.team209.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 3/18/18.
 */
public class UpdateCourseResult {
    private boolean success;

    private Course course;

    private List<String> errorMessages;

    /**
     * default constructor
     */
    public UpdateCourseResult() {
    }

    /**
     * encapsulate the course inside updateUserResult
     * @param course the course updated
     */
    public UpdateCourseResult(Course course) {
        if (course != null) {
            this.success = true;
        } else {
            this.success = false;
            this.errorMessages = new ArrayList<>();
            errorMessages.add("Error.");
        }

        this.course = course;
    }

    /**
     * encapsulate the update failure msg
     * @param errorMessages the update course error msg
     */
    public UpdateCourseResult(List<String> errorMessages) {
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
     * get course updated
     * @return the updated user
     */
    public Course getCourse() {
        return course;
    }

    /**
     * set course
     * @param course set the updated course
     */
    public void setCourse(Course course) {
        this.course = course;
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
