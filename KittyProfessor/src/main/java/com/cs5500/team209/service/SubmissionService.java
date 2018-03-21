package com.cs5500.team209.service;

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
     * validate submission having the required fields
     * @param submission the submission to be validated
     * @return the error messages if any
     */
    private List<String> validateSubmission(Submission submission) {
        List<String> errorMessages = new ArrayList<>();
        if (submission.getAssignment() == null) {
            errorMessages.add("Assignment field can't be null.");
        }
        if (submission.getUser() == null) {
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
