package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

/**
 * Created by mengtao on 3/19/18.
 *
 * Submission class for user joined as a student
 */
public class Submission {

    @Id
    private String id;
    private Assignment assignment;
    private User user;
    private String path;

    /**
     * Setter and getters
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Submission(Assignment assignment, User user) {
        this.assignment = assignment;
        this.user = user;
    }


}
