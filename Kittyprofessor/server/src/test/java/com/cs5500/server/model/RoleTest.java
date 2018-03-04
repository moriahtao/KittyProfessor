package com.cs5500.server.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 3/4/18.
 */
public class RoleTest {
    @Test
    public void getRole() {
        Role role = new Role("ROLE_ADMIN");
        role.setAuthority("ROLE_USER");
        assertEquals("ROLE_USER", role.getAuthority());
    }

}