package com.cs5500.team209.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 3/4/18.
 *
 * Tests for Role class
 */
public class RoleTest {

    /**
     * gets get role
     */
    @Test
    public void getRole() {
        Role role = new Role("ROLE_ADMIN");
        role.setAuthority("ROLE_USER");
        assertEquals("ROLE_USER", role.getAuthority());
    }

}