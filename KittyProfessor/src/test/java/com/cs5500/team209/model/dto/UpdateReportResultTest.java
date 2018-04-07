package com.cs5500.team209.model.dto;

import org.junit.Test;
import com.cs5500.team209.model.Report;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UpdateReportResultTest {

    @Test
    public void getReport() {
        UpdateReportResult updateReportResult = new UpdateReportResult();
        Report report = new Report("Assignment1","student1","student2","result",45.0);
        UpdateReportResult updateReportResult1 = new UpdateReportResult(report);
        ArrayList<String> errors = new ArrayList<String>();
        errors.add("file too big");
        errors.add("wrong file format");
        updateReportResult.setReport(report);
        updateReportResult1.setSuccess(true);
        updateReportResult1.setErrorMessages(errors);
        assertEquals(errors.get(0),updateReportResult1.getErrorMessages().get(0));
        updateReportResult1.setReport(report);
        assertEquals(report,updateReportResult1.getReport());
        assertEquals(true,updateReportResult1.isSuccess());
    }
}