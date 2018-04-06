package com.cs5500.team209.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginTest {

    /**
     * This is to test the Login class
     * The test checks if user details are getting created correctly and
     * adding the required data*/
    @Test
    public void checkLogin() {
        Login login = new Login();
        login.setUserName("Bob");
        login.setPassword("thisisbob");
        assertEquals("Bob",login.getUserName());
        assertEquals("thisisbob",login.getPassword());
    }
}