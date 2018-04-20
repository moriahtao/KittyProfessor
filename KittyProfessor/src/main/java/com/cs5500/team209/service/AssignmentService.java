package com.cs5500.team209.service;

import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.dto.UpdateAssignmentResult;
import com.cs5500.team209.repository.AssignmentRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 3/19/18.
 *
 * Assignment related services
 */
@Service
public class AssignmentService {

    @Autowired
    AssignmentRepository assignmentRepository;

    final static Logger logger = Logger.getLogger(AssignmentService.class);

    public UpdateAssignmentResult createAssignment(Assignment assignment) {
        List<String> errorMessages = validateAssignment(assignment);
        logger.info(errorMessages);
        if (errorMessages == null || errorMessages.isEmpty()) {
            Assignment createdAssignment = assignmentRepository.save(assignment);
            logger.info("Successfully added");
            return new UpdateAssignmentResult(createdAssignment);
        } else {
            return new UpdateAssignmentResult(errorMessages);
        }
    }

    public Assignment getAssignmentById(String assignmentId) {
        return assignmentRepository.findAssignmentByAssignmentId(assignmentId);
    }

    /**
     * find all assignments for the course
     * @return a list of assignments
     */
    public List<Assignment> getAssignmentsForCourse(String courseId) {
        return assignmentRepository.findAssignmentsByCourseId(courseId);
    }

    public void deleteAssignment(String assignmentId) {
        assignmentRepository.deleteById(assignmentId);
    }

    public void deleteAssignmentByUserName(String userName) {
        assignmentRepository.deleteAssignmentByUserName(userName);
    }

    /**
     * validate assignment having the required fields
     * @param assignment the assignment to be validated
     * @return the error messages if any
     */
    private List<String> validateAssignment(Assignment assignment) {
        List<String> errorMessages = new ArrayList<>();
        checkNull(errorMessages, assignment.getName(), "Assignment Name");
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
