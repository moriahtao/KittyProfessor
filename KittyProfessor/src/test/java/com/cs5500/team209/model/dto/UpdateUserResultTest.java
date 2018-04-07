package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 3/4/18.
 *
 * Tests for clas UpdateUserResult
 */
public class UpdateUserResultTest {

    /**
     * should fetch corresponding fields after updating
     * user
     */
    @Test
    public void testUpdateUserSuccessResult() {
        User user = new User("joe", "1234", "student", "neu", "example@gmail");
        UpdateUserResult res = new UpdateUserResult(user);
        res.setUser(user);
        res.setSuccess(true);
        assertEquals(user, res.getUser());
        assertEquals(true, res.isSuccess());
        res = new UpdateUserResult();
        user = null;
        res = new UpdateUserResult(user);
        assertEquals(false, res.isSuccess());
        List<String> errors = new ArrayList<>();
        String errMsg = "Error.";
        errors.add(errMsg);
        assertEquals(errors, res.getErrorMessages());
    }

    /**
     * should return error msg
     * after updating user fails
     */
    @Test
    public void testUpdateUserFailureResult() {
        List<String> errors = new ArrayList<>();
        String errMsg = "user not exist!";
        errors.add(errMsg);

        UpdateUserResult res = new UpdateUserResult(errors);
        res.setErrorMessages(errors);
        res.setSuccess(false);
        assertEquals(false, res.isSuccess());
        assertEquals(errors, res.getErrorMessages());
    }

}