package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.User;

import java.util.List;

/**
 * Created by mengtao on 2/28/18.
 */
public class FetchUserResult {
    private User user;

    private List<User> userList;

    public FetchUserResult(User user) {
        if(user != null) {
            user.setPassword(null);
            this.user = user;
        }
    }

    public FetchUserResult(List<User> users) {
        for (User user : users) {
            user.setPassword(null);
        }
        this.userList = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
