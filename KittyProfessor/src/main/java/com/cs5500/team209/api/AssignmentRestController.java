package com.cs5500.team209.api;

import com.cs5500.team209.WebUtils;
import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.dto.UpdateAssignmentResult;
import com.cs5500.team209.service.AssignmentService;
import com.cs5500.team209.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by mengtao on 3/19/18.
 *
 * Assignment related rest apis
 */
@RestController
@Controller
public class AssignmentRestController {
    @Autowired
    AssignmentService assignmentService;

    @Autowired
    CourseService courseService;

    /**
     * rest api for creating an assignment
     * @param assignment the assignment object to be created
     * @return response with assignment and http status
     */
    @RequestMapping(method=POST, path="/api/course/{courseId}/assignments")
    public Object createAssignment(@PathVariable("courseId") String courseId,
                                   @RequestBody Assignment assignment) {
        if (courseId != null) {
            assignment.setCourseId(courseId);
            UpdateAssignmentResult result = assignmentService.createAssignment(assignment);
            if (result.isSuccess()) {
                return WebUtils.createSuccessMap(result.getAssignment());
            } else {
                return WebUtils.failedMap(result.getErrorMessages());
            }
        } else {
            return WebUtils.failedMapWithStatus("Course Id Not Found.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * rest api for get all assignments for the course
     * @return response with assignments and http status
     */
    @RequestMapping(method=GET, path="/api/course/{courseId}/assignment")
    public Object getAssignmentsForCourse(@PathVariable("courseId") String courseId) {
        List<Assignment> assignments = assignmentService.getAssignmentsForCourse(courseId);
        return WebUtils.successMap(assignments);
    }
}
