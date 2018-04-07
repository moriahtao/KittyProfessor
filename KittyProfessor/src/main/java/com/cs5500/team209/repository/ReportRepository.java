package com.cs5500.team209.repository;

import com.cs5500.team209.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by mengtao on 4/4/18.
 */
public interface ReportRepository extends MongoRepository<Report, String> {
    /**
     * find report by id
     * @param reportId the id for query
     * @return the report found
     */
    Report findReportByReportId(String reportId);

    /**
     * find list of reports by assignmentId
     * @param assignmentId the id used for query
     * @return list of reports
     */
    List<Report> findReportByAssignmentId(@Param("assignmentId") String assignmentId);

}
