package com.cs5500.team209.service;

import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.Course;
import com.cs5500.team209.model.dto.UpdateAssignmentResult;
import com.cs5500.team209.model.dto.UpdateCourseResult;
import com.cs5500.team209.repository.AssignmentRepository;
import com.cs5500.team209.repository.CourseRepository;
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

    public UpdateAssignmentResult createAssignment(Assignment assignment) {
        List<String> errorMessages = validateAssignment(assignment);
        System.out.println(errorMessages);
        if (errorMessages == null || errorMessages.isEmpty()) {
            Assignment createdAssignment = assignmentRepository.save(assignment);
            System.out.println("Successfully added");
            return new UpdateAssignmentResult(createdAssignment);
        } else {
            return new UpdateAssignmentResult(errorMessages);
        }
    }

    /**
     * find all assignments for the course
     * @return a list of assignments
     */
    public List<Assignment> getAssignmentsForCourse(String courseId) {
        return assignmentRepository.findAssignmentsByCourseID(courseId);
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
