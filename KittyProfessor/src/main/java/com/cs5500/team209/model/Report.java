package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by mengtao on 4/3/18.
 */
public class Report {

    @Id
    private String reportId;
    private String submissionId1;
    private String submissionId2;
    private String filePath;



    public Report(String submissionId1, String submissionId2, String filePath) {
        this.submissionId1 = submissionId1;
        this.submissionId2 = submissionId2;
        this.filePath = filePath;
    }


    public String getSubmissionId1() {
        return submissionId1;
    }

    public void setSubmissionId1(String submissionId1) {
        this.submissionId1 = submissionId1;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSubmissionId2() {
        return submissionId2;
    }

    public void setSubmissionId2(String submissionId2) {
        this.submissionId2 = submissionId2;
    }

}
