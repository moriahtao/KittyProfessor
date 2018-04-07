package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.User;

import java.util.List;

/**
 * Created by mengtao on 2/28/18.
 *
 * the fetch user result
 * containing Spring Security needed fields
 */
public class FetchUserResult {
    private User user;

    private List<User> userList;

    /**
     * the fetched user result
     * @param user the user fetched
     */
    public FetchUserResult(User user) {
        if(user != null) {
            user.setPassword(null);
            this.user = user;
        }
    }

    /**
     * the fetched user list
     * @param users the user list fetched
     */
    public FetchUserResult(List<User> users) {
        for (User user : users) {
            user.setPassword(null);
        }
        this.userList = users;
    }

    /**
     * get fetched user
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * set fetched user
     * @param user the user to be set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * get a list of user fetched
     * @return user list fetched
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * set the list of users fetched
     * @param userList user list
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
