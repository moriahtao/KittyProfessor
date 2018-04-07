package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Report;

/**
 * Created by mengtao on 4/5/18.
 */
public class ReportDisplay {
    private String user1;
    private String user2;
    private Report report;

    public ReportDisplay(Report report) {
        this.report = report;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }



}
