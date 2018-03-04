package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 2/28/18.
 */
public class UpdateUserResult {
    private boolean success;

    private User user;

    private List<String> errorMessages;

    public UpdateUserResult() {
    }

    public UpdateUserResult(User user) {
        if (user != null) {
            user.setPassword(null);
            this.success = true;
        } else {
            this.success = false;
            this.errorMessages = new ArrayList<>();
            errorMessages.add("Error.");
        }

        this.user = user;
    }

    public UpdateUserResult(List<String> errorMessages) {
        this.success = false;
        this.errorMessages = errorMessages;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
