package com.cs5500.server.model.dto;

import com.cs5500.server.model.Role;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 3/4/18.
 */
public class MyUserRoleTest {
    @Test
    public void testMyUserRole() {
        Role role = new Role("ROLE_ADMIN");
        MyUserRole myUserRole = new MyUserRole(role);
        assertEquals("ROLE_ADMIN", myUserRole.getAuthority());
    }

}