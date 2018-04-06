package com.cs5500.team209.service;

import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.Course;
import com.cs5500.team209.model.Submission;
import com.cs5500.team209.model.dto.UpdateSubmissionResult;
import com.cs5500.team209.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 3/19/18.
 *
 * User joinedAs student Submission related services
 */
@Service
public class SubmissionService {

    @Autowired
    SubmissionRepository submissionRepository;

    public UpdateSubmissionResult createSubmission(Submission submission) {
        List<String> errorMessages = validateSubmission(submission);
        if (errorMessages == null || errorMessages.isEmpty()) {
            Submission createdSubmission = submissionRepository.save(submission);
            return new UpdateSubmissionResult(createdSubmission);
        } else {
            return new UpdateSubmissionResult(errorMessages);
        }
    }

    /**
     * find the specific submission
     * @param id the query submission id
     * @return the submission to be queried
     */
    public Submission getSubmissionById(String id) {
        return submissionRepository.findSubmissionBySubmissionId(id);
    }


    /**
     * add new file to one submission
     * and store new file path
     * @param filePath the path for the new file
     * @param submission the submission to add file path
     * @return the updated submission with new list of file paths
     */
    public UpdateSubmissionResult addFileToSubmission(String filePath, Submission submission) {
        List<String> currentFiles = submission.getFilePaths();
        currentFiles.add(filePath);
        submission.setFilePaths(currentFiles);
        return new UpdateSubmissionResult(submissionRepository.save(submission));
    }

    /**
     * find all assignments for the course
     * @return a list of assignments
     */
    public List<Submission> getSubmissionsForAssignment(String assignmentId, String username) {
        return submissionRepository.findSubmissionsByCriteria(assignmentId, username);
    }

    public List<Submission> getOtherStudentSubmissions(String submissionId, String username) {
        return submissionRepository.findOtherStudentSubmissions(submissionId, username);
    }

    public List<Submission> getSubmissionByUsername(String username) {
        return submissionRepository.findSubmissionByUsername(username);
    }

    /**
     * validate submission having the required fields
     * @param submission the submission to be validated
     * @return the error messages if any
     */
    private List<String> validateSubmission(Submission submission) {
        List<String> errorMessages = new ArrayList<>();
        if (submission == null) {
            errorMessages.add("Assignment field can't be null.");
        } else if (submission.getUsername().isEmpty()) {
            errorMessages.add("User field can't be null.");
        }
        return errorMessages;
    }

    /**
     * check if fields are null
     * @param errorMessages error msg
     * @param value the value to be checked
     * @param fieldName the field to be checked
     */
    private void checkNull(List<String> errorMessages, String value, String fieldName) {
        if (StringUtils.isEmpty(value)) {
            errorMessages.add(fieldName + " can't be null.");
        }
    }
}
