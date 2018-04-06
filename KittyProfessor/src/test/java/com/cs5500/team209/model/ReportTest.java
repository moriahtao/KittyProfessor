package com.cs5500.team209.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReportTest {
    /**
     * This is to test the Report class
     * The test checks if Reports are getting created correctly and
     * adding the required data*/

    @Test
    public void reportTest() {
        Report report = new Report("Assignment1","student1","student2","result",45.0);
        report.setAssignmentId("Assignment1");
        report.setSubmissionId1("student1");
        report.setSubmissionId2("student2");
        report.setFilePath("result");
        report.setReportId("report1");
        report.setScore(45.5);
        assertEquals("Assignment1",report.getAssignmentId());
        assertEquals("student1",report.getSubmissionId1());
        assertEquals("student2",report.getSubmissionId2());
        assertEquals("result",report.getFilePath());
        assertEquals("report1",report.getReportId());
        assertEquals(45.5,report.getScore(),0.1);
    }
}