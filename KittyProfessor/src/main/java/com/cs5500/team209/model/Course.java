package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

/**
 * Created by mengtao on 3/18/18.
 *
 * the Course class
 */
public class Course {

    @Id
    private String id;

    private String name;

    /**
     * Default constructor
     */
    public Course() {}

    public Course(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Setters and Getters
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
