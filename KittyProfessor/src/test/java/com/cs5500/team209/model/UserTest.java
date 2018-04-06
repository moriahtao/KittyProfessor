package com.cs5500.team209.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 2/28/18.
 *
 * Tests for User class
 */
public class UserTest {

    /**
     * should get user fields after creating user correctly
     */
    @Test
    public void getUser() {
        User user = new User("joe", "1234", "student", "neu", "example@gmail");
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role2);
        user.setEmail("example@gmail");
        user.setUniversity("neu");
        user.setUsername("joe");
        user.setRoles(roleList);
        user.setJoinAs("student");
        user.setFirstName("joe");
        user.setLastName("Wood");
        user.setPassword("1234");

        assertEquals("example@gmail", user.getEmail());
        assertEquals("joe", user.getUsername());
        assertEquals("1234", user.getPassword());
        assertEquals("student", user.getJoinAs());
        assertEquals("neu", user.getUniversity());
        assertEquals("joe", user.getFirstName());
        assertEquals("Wood", user.getLastName());

        assertEquals("example@gmail", user.getEmail());
        assertEquals(roleList, user.getRoles());
    }


}