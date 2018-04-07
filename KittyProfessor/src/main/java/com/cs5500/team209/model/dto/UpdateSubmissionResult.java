package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Submission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 3/20/18.
 */
public class UpdateSubmissionResult {
    private boolean success;

    private Submission submission;

    private List<String> errorMessages;

    /**
     * default constructor
     */
    public UpdateSubmissionResult() {
    }

    /**
     * encapsulate the assignment inside UpdateAssignmentResult
     * @param submission the assignment updated
     */
    public UpdateSubmissionResult(Submission submission) {
        if (submission != null) {
            this.success = true;
        } else {
            this.success = false;
            this.errorMessages = new ArrayList<>();
            errorMessages.add("Error.");
        }

        this.submission = submission;
    }

    /**
     * encapsulate the update failure msg
     * @param errorMessages the update assignment error msg
     */
    public UpdateSubmissionResult(List<String> errorMessages) {
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
     * get submission updated
     * @return the updated submission
     */
    public Submission getSubmission() {
        return submission;
    }

    /**
     * set submission
     * @param submission set the updated submission
     */
    public void setSubmission(Submission submission) {
        this.submission = submission;
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
