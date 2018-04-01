package com.cs5500.team209.repository;

import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by mengtao on 3/19/18.
 *
 * java repo for Submission in mongodb Submission table
 */
public interface SubmissionRepository extends MongoRepository<Submission, String> {
    /**
     * find submission by id
     * @param submissionId the id for query
     * @return the submission found
     */
    Submission findSubmissionById(String submissionId);

    List<Submission> findSubmissionsByAssignmentId(@Param("assignmentId") String assignmentId);

}
