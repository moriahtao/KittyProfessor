package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Report;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReportDisplayTest {

    @Test
    public void getReport() {
        Report report = new Report("Assignment1","student1","student2",
                "courseInfo1", "courseInfo2","result",45.0);
        ReportDisplay reportDisplay = new ReportDisplay(report);
        reportDisplay.setReport(report);
        reportDisplay.setUser1("username1");
        reportDisplay.setUser2("username2");
        assertEquals("username1", reportDisplay.getUser1());
        assertEquals("username2", reportDisplay.getUser2());
        assertEquals(report, reportDisplay.getReport());
    }
}