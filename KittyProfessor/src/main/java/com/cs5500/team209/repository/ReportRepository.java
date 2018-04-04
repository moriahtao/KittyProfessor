package com.cs5500.team209.repository;

import com.cs5500.team209.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

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

}
