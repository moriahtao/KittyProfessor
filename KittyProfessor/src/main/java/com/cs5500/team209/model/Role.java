package com.cs5500.team209.model;

import org.springframework.data.annotation.Id;

/**
 * Created by mengtao on 2/26/18.
 *
 * Roles of User
 */
public class Role {

    /**
     * get role
     * @return the role
     */
    public String getAuthority() {
        return authority;
    }

    @Id
    private String authority;

    /**
     * default constructor
     */
    public Role(){
    }

    /**
     * constructor with role name
     * @param name the role name
     */
    public Role(String name){
        authority = name;
    }

    /**
     * setter of role
     * @param authority the role to be set
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
