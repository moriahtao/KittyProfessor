package com.cs5500.team209.repository;

import com.cs5500.team209.model.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by mengtao on 3/19/18.
 *
 * java repo for Assignment in mongodb Assignment table
 */
public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    /**
     * find assignments by courseId
     * @param assignmentId the courseId for query
     * @return the assignments found
     */
    Assignment findAssignmentByAssignmentId(String assignmentId);
    /**
     * find assignment by id
     * @param courseId the id for query
     * @return the assignment found
     */
    List<Assignment> findAssignmentsByCourseId(@Param("courseId") String courseId);
    void deleteAssignmentByUserName(@Param("userName") String userName);

}
