package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Role;
import com.cs5500.team209.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 3/4/18.
 *
 * Tests for MyUserPrinciple
 */
public class MyUserPrincipleTest {

    /**
     * should fetch corresponding fields after creating
     * MyUserPrinciple obj
     */
    @Test
    public void testMyUserPrinciple() {
        User user = new User("joe", "1234", "student", "neu", "example@gmail");
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        List<MyUserRole> roleList = new ArrayList<>();
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role1);
        userRoles.add(role2);
        MyUserRole myRole1 = new MyUserRole(role1);
        MyUserRole myRole2 = new MyUserRole(role2);
        roleList.add(myRole1);
        roleList.add(myRole2);
        user.setRoles(userRoles);
        MyUserPrinciple myUserPrinciple = new MyUserPrinciple(user);
        assertEquals(false, myUserPrinciple.getAuthorities().isEmpty());
        assertEquals("1234", myUserPrinciple.getPassword());
        assertEquals("joe", myUserPrinciple.getUsername());
        assertEquals(true, myUserPrinciple.isAccountNonExpired());
        assertEquals(true, myUserPrinciple.isAccountNonLocked());
        assertEquals(true, myUserPrinciple.isCredentialsNonExpired());
        assertEquals(true, myUserPrinciple.isEnabled());

    }


}