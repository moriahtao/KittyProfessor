package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Assignment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 3/19/18.
 *
 * the updated assignment obj
 */
public class UpdateAssignmentResult {
    private boolean success;

    private Assignment assignment;

    private List<String> errorMessages;

    /**
     * default constructor
     */
    public UpdateAssignmentResult() {
    }

    /**
     * encapsulate the assignment inside UpdateAssignmentResult
     * @param assignment the assignment updated
     */
    public UpdateAssignmentResult(Assignment assignment) {
        if (assignment != null) {
            this.success = true;
        } else {
            this.success = false;
            this.errorMessages = new ArrayList<>();
            errorMessages.add("Error.");
        }

        this.assignment = assignment;
    }

    /**
     * encapsulate the update failure msg
     * @param errorMessages the update assignment error msg
     */
    public UpdateAssignmentResult(List<String> errorMessages) {
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
     * get assignment updated
     * @return the updated assignment
     */
    public Assignment getAssignment() {
        return assignment;
    }

    /**
     * set assignment
     * @param assignment set the updated assignment
     */
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
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
