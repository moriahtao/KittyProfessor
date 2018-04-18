package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

/**
 * Created by mengtao on 4/3/18.
 */
public class Report {

    @Id
    private String reportId;
    private String assignmentId;
    private String submissionId1;
    private String submissionId2;
    private String courseInfo1;
    private String courseInfo2;
    private String filePath;
    private double score;



    public Report(String assignmentId, String submissionId1,
                  String submissionId2,
                  String courseInfo1, String courseInfo2,
                  String filePath, double score) {
        this.courseInfo1 = courseInfo1;
        this.courseInfo2 = courseInfo2;
        this.assignmentId = assignmentId;
        this.submissionId1 = submissionId1;
        this.submissionId2 = submissionId2;
        this.filePath = filePath;
        this.score = score;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
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
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getCourseInfo1() {
        return courseInfo1;
    }

    public void setCourseInfo1(String courseInfo1) {
        this.courseInfo1 = courseInfo1;
    }

    public String getCourseInfo2() {
        return courseInfo2;
    }

    public void setCourseInfo2(String courseInfo2) {
        this.courseInfo2 = courseInfo2;
    }

}
