package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

/**
 * Created by mengtao on 3/18/18.
 *
 * the Course class
 */
public class Course {

    @Id
    private String courseId;

    private String courseCode;

    private String name;

    private String term;

    private int numAssignments;

    private int numStudents;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Default constructor
     */
    public Course() {}

    /**
     * Setters and Getters
     */
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumAssignments() {
        return numAssignments;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumAssignments(int numAssignments) {
        this.numAssignments = numAssignments;
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }



}
