package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Role;
import com.cs5500.team209.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mengtao on 2/27/18.
 *
 * Spring Security requires class User to implement UserDetails
 * MyUserPrinciple added to serve as this purpose
 */
public class MyUserPrinciple implements UserDetails{

    private User user;

    /**
     * encapsulate our user
     * @param user our user
     */
    public MyUserPrinciple(User user) {
        this.user = user;
    }

    /**
     * setters and getters
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<MyUserRole> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(new MyUserRole(role));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
