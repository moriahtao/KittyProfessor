package com.login.server;

import org.springframework.data.annotation.Id;

/**
 * Created by mengtao on 2/25/18.
 */
public class User {
    @Id
    public String id;

    public String username;
    public String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, username, password);
    }

}
