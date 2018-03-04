package com.cs5500.team209;

import com.cs5500.team209.model.User;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 3/4/18.
 */
public class WebUtilsTest {
    @Test
    public void successMapTest() {
        User user = new User("ann", "1234", "student", "neu", "example@gmail");
        assertEquals(HttpStatus.OK, WebUtils.successMap(user).getStatusCode());
    }

    @Test
    public void createSuccessMapTest() {
        User user = new User("ann", "1234", "student", "neu", "example@gmail");
        assertEquals(HttpStatus.CREATED, WebUtils.createSuccessMap(user).getStatusCode());
    }

    @Test
    public void failedMapTest() {
        assertEquals(HttpStatus.OK, WebUtils.failedMap("Error").getStatusCode());
    }


    @Test
    public void failedMapWithStatusTest() {
        assertEquals(HttpStatus.OK, WebUtils.failedMapWithStatus("Error", HttpStatus.OK).getStatusCode());
    }

}