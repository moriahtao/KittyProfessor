package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 3/19/18.
 *
 * Submission class for user joined as a student
 */
public class Submission {

    @Id
    private String submissionId;
    private String assignmentId;
    private String username;
    private List<String> filePaths;


    public Submission(String assignmentId, String username, List<String> filePaths) {
        this.assignmentId = assignmentId;
        this.username = username;
        this.filePaths = new ArrayList<>(filePaths);
    }

    public Submission(String assignmentId, String username) {
        this.assignmentId = assignmentId;
        this.username = username;
        this.filePaths = new ArrayList<>();
    }
    /**
     * Setter and getters
     */
    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setPath(List<String> filePaths) {
        this.filePaths = new ArrayList<>(filePaths);
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }


}
