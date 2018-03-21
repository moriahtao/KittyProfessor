package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

/**
 * Created by mengtao on 3/19/18.
 *
 * Assignment class
 */
public class Assignment {

    @Id
    private String id;
    private String name;
    private int number;

    private Course course;

    /**
     * Default constructor
     */
    public Assignment(){}

    public Assignment(String name, Course course, int number) {
        this.name = name;
        this.course = course;
        this.number = number;
    }

    public Assignment(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }




}
