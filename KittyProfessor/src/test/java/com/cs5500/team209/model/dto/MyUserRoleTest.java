package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Role;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 3/4/18.
 *
 * Tests for MyUserRole
 */
public class MyUserRoleTest {

    /**
     * should get created roles
     */
    @Test
    public void testMyUserRole() {
        Role role = new Role("ROLE_ADMIN");
        MyUserRole myUserRole = new MyUserRole(role);
        assertEquals("ROLE_ADMIN", myUserRole.getAuthority());
    }

}