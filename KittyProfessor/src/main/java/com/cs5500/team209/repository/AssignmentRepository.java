package com.cs5500.team209.repository;

import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.Course;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
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
     * @param courseId the courseId for query
     * @return the assignments found
     */
    @Query("{'course.id' : ?0}")
    List<Assignment> findAssignmentsForCourse(@Param("id") String courseId);
}
