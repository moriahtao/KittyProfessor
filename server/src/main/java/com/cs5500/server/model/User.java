package com.cs5500.server.model;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mengtao on 2/25/18.
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


    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role("ROLE_USER"));
        this.roles = roles;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, username='%s']",
                id, username);
    }

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
