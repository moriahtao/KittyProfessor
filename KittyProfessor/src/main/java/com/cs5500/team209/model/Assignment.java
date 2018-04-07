package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

/**
 * Created by mengtao on 3/19/18.
 *
 * Assignment class
 * This class creates Assignements
 * This class has methods to set and retrive the various functionalities related to assignments
 * the main components of assignment would be assigmmentID, assigmmentName, coursID , courseName,
 * threshold , due date and description.
 */
public class Assignment {

    @Id
    private String assignmentId;
    private String userName;
    private String name;
    private String courseId;
    private String desc;
    private int threshold;
    private String due;
    private String language;

    /**
     * Default constructor
     */
    public Assignment(){}

    /**
     * method gets the assignmentID
     * No parameters
     * @return String which is the assignemntID
     */
    public String getAssignmentId() {
        return assignmentId;
    }


    /**
     * method sets the assignmentID
     * assignmentID String
     * @return Nothing
     */
    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    /**
     * method gets the assignmentName
     * No parameters
     * @return String which is the assignemntName
     */
    public String getName() {
        return name;
    }

    /**
     * method sets the Name of the assignment
     * name String
     * @return Nothing
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method gets the courseID
     * No parameters
     * @return String which is the courseID
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * method sets the courseID
     * courseID String
     * @return Nothing
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * method gets the threshold
     * No parameters
     * @return int which is the threshold
     */
    public int getThreshold() {
        return threshold;
    }


    /**
     * method sets the threshold
     * threshold Int
     * @return Nothing
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    /**
     * method gets the due date
     * No parameters
     * @return String which is the due date
     */
    public String getDue() {
        return due;
    }

    /**
     * method sets the due date
     * due Int
     * @return Nothing
     */
    public void setDue(String due) {
        this.due = due;
    }
    /**
     * method gets the assignment description
     * No parameters
     * @return String which is the description
     */
    public String getDesc() {
        return desc;
    }


    /**
     * method sets the description
     * desc String
     * @return Nothing
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
