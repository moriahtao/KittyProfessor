package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

/**
 * Created by mengtao on 3/19/18.
 *
 * Assignment class
 */
public class Assignment {

    @Id
    private String assignmentId;
    private String name;
    private String courseID;
    private String desc;
    private int threshold;
    private String due;

    /**
     * Default constructor
     */
    public Assignment(){}

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
