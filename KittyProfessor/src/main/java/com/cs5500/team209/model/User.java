package com.cs5500.team209.model;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 2/25/18.
 *
 * the User class
 */
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<Role> roles;
    private String joinAs;
    private String university;
    private String email;
    /**
     * default constructor
     */
    public User() {}

    /**
     * constructor
     * @param username username
     * @param password password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role("ROLE_USER"));
        this.roles = roles;
    }

    /**
     * constructor
     * @param username username
     * @param password password
     * @param joinAs join as identity
     * @param university university name
     * @param email email address
     */
    public User(String username, String password, String joinAs, String university, String email) {
        this.username = username;
        this.password = password;
        this.joinAs = joinAs;
        this.email = email;
        this.university = university;
        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role("ROLE_USER"));
        this.roles = roles;
    }

    /**
     * constructor
     * @param username username
     * @param password password
     * @param roles roles of the user
     */
    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        List<Role> rolesList = new ArrayList<Role>();
        rolesList.addAll(roles);
        this.roles = rolesList;
    }

    /**
     * toString method
     * @return String
     */
    @Override
    public String toString() {
        return String.format(
                "User[id=%s, username='%s']",
                id, username);
    }

    /**
     * setters and getters
     */
    /**
     * join as "student" or "instructor"
     * @return the join as identity
     */
    public String getJoinAs() {
        return joinAs;
    }

    /**
     * set join as
     * @param joinAs the identity to join as
     */
    public void setJoinAs(String joinAs) {
        this.joinAs = joinAs;
    }

    /**
     * the university of the user went
     * @return the university name
     */
    public String getUniversity() {
        return university;
    }

    /**
     * the university of the user
     * @param university the university name
     */
    public void setUniversity(String university) {
        this.university = university;
    }

    /**
     * get email address
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * set email address
     * @param email the email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * id
     * @return id
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


}
