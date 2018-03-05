package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Role;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by mengtao on 2/27/18.
 *
 * The Role class required by Spring Security
 * KittyProfessor role encapsulated
 */
public class MyUserRole implements GrantedAuthority {

    private Role role;

    /**
     * constructor
     * @param role the role to be created
     */
    public MyUserRole(Role role) {
        this.role = role;
    }

    /**
     * get role
     * @return the role
     */
    @Override
    public String getAuthority() {
        return role.getAuthority();
    }
}
