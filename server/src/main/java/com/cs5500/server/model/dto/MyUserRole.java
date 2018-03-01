package com.cs5500.server.model.dto;

import com.cs5500.server.model.Role;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by mengtao on 2/27/18.
 */
public class MyUserRole implements GrantedAuthority {

    private Role role;

    public MyUserRole(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getAuthority();
    }
}
