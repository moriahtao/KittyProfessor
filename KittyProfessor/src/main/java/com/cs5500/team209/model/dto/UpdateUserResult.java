package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 2/28/18.
 *
 * the updated user result
 */
public class UpdateUserResult {
    private boolean success;

    private User user;

    private List<String> errorMessages;

    /**
     * default constructor
     */
    public UpdateUserResult() {
    }

    /**
     * encapsulate the user inside updateUserResult
     * @param user the user updated
     */
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

    /**
     * encapsulate the update failure msg
     * @param errorMessages the update user error msg
     */
    public UpdateUserResult(List<String> errorMessages) {
        this.success = false;
        this.errorMessages = errorMessages;
    }

    /**
     * if the update is successful
     * @return true if successfully updated
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * set if the update is successful or not
     * @param success true if successful
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * get user updated
     * @return the updated user
     */
    public User getUser() {
        return user;
    }

    /**
     * set user
     * @param user set the updated user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * get the update error msg
     * @return the list of error msg
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * set the update error msg
     * @param errorMessages the list of error msg
     */
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
