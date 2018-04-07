package com.cs5500.team209.repository;

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
    Submission findSubmissionBySubmissionId(String submissionId);

    @Query("{assignmentId: ?0, username: ?1}")
    List<Submission> findSubmissionsByCriteria
            (String assignmentId, String username);
    @Query("{assignmentId: ?0, username: { $ne: ?1 }}")
    List<Submission> findOtherStudentSubmissions
            (String assignmentId, String username);
    List<Submission> findSubmissionByUsername
            (@Param("username") String username);

}
