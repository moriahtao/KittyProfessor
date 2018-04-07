package com.cs5500.team209;

import com.cs5500.team209.model.User;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 3/4/18.
 *
 * Tests for WebUtils
 */
public class WebUtilsTest {

    /**
     * should return status 200 when fetch user successfully
     */
    @Test
    public void successMapTest() {
        User user = new User("ann", "1234", "student", "neu", "example@gmail");
        assertEquals(HttpStatus.OK, WebUtils.successMap(user).getStatusCode());
    }

    /**
     * should return code 201 when fetch user successfully
     */
    @Test
    public void createSuccessMapTest() {
        User user = new User("ann", "1234", "student", "neu", "example@gmail");
        assertEquals(HttpStatus.CREATED, WebUtils.createSuccessMap(user).getStatusCode());
    }

    /**
     * should return 200 when expected exception happens
     */
    @Test
    public void failedMapTest() {
        assertEquals(HttpStatus.OK, WebUtils.failedMap("Error").getStatusCode());
    }


    /**
     * should return error code when unexpected exception or err happens
     */
    @Test
    public void failedMapWithStatusTest() {
        assertEquals(HttpStatus.OK, WebUtils.failedMapWithStatus("Error", HttpStatus.OK).getStatusCode());
    }

}