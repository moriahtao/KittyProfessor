package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Report;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 4/4/18.
 */
public class UpdateReportResult {
    private boolean success;

    private Report report;

    private List<String> errorMessages;

    /**
     * default constructor
     */
    public UpdateReportResult() {
    }

    /**
     * encapsulate the report inside UpdateReportResult
     * @param report the report updated
     */
    public UpdateReportResult(Report report) {
        if (report != null) {
            this.success = true;
        } else {
            this.success = false;
            this.errorMessages = new ArrayList<>();
            errorMessages.add("Error.");
        }

        this.report = report;
    }

    /**
     * encapsulate the update failure msg
     * @param errorMessages the update assignment error msg
     */
    public UpdateReportResult(List<String> errorMessages) {
        this.success = false;
        this.errorMessages = errorMessages;
    }

    /**
     * if the update is successful
     * @return true if successfully updated
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * set if the update is successful or not
     * @param success true if successful
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * get report updated
     * @return the updated report
     */
    public Report getReport() {
        return report;
    }

    /**
     * set report
     * @param report set the updated report
     */
    public void setReport(Report report) {
        this.report = report;
    }

    /**
     * get the update error msg
     * @return the list of error msg
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * set the update error msg
     * @param errorMessages the list of error msg
     */
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
