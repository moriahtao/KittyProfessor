package com.login.server;

import org.springframework.context.annotation.Primary;
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
public class User implements UserDetails {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    private String password;

    private List<Role> roles;


    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role("ROLE_ADMIN"));
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
