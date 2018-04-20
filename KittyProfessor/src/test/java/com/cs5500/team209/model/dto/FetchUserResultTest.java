package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Role;
import com.cs5500.team209.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 3/3/18.
 *
 * Tests for FetchUserResult
 */
public class FetchUserResultTest {

    /**
     * should fetch user after creating user
     */
    @Test
    public void fetchUser() {
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
        FetchUserResult fetchUserResult = new FetchUserResult(user);
        assertEquals("example@gmail", user.getEmail());
        assertEquals("joe", user.getUsername());
        assertEquals("student", user.getJoinAs());
        assertEquals("neu", user.getUniversity());
        assertEquals("joe", user.getFirstName());
        assertEquals("Wood", user.getLastName());
        assertEquals("example@gmail", user.getEmail());
        assertEquals(roleList, user.getRoles());
        assertEquals(user, fetchUserResult.getUser());
        User newUser = new User("ann", "1234", "student", "neu", "example.@gmail");
        fetchUserResult.setUser(newUser);
        assertEquals(newUser, fetchUserResult.getUser());

    }

    /**
     * should fetch user list
     */
    @Test
    public void fetchUserList() {
        User user = new User("joe", "1234", "student", "neu", "example.@gmail");
        List<User> users = new ArrayList<>();
        users.add(user);
        FetchUserResult fetchUserResultList = new FetchUserResult(users);
        fetchUserResultList.setUserList(users);
        assertEquals(users, fetchUserResultList.getUserList());

    }


}