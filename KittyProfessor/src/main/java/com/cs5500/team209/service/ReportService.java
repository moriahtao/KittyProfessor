package com.cs5500.team209.service;

import com.cs5500.team209.model.Report;
import com.cs5500.team209.model.dto.UpdateReportResult;
import com.cs5500.team209.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mengtao on 4/3/18.
 */
@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    public UpdateReportResult createReport(Report report) {
        Report createdReport = reportRepository.save(report);
        return new UpdateReportResult(createdReport);
    }

}
