package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

/**
 * Created by mengtao on 3/18/18.
 *
 * the Course class
 */
public class StudentCourse {

    public String getStudentCourseId() {
        return studentCourseId;
    }

    public void setStudentCourseId(String studentCourseId) {
        this.studentCourseId = studentCourseId;
    }

    @Id
    private String studentCourseId;

    private String courseId;

    private String userName;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public StudentCourse() {

    }
}
