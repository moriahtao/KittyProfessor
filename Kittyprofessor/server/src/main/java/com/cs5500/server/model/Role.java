package com.cs5500.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by mengtao on 2/26/18.
 */
public class Role {

    public String getAuthority() {
        return authority;
    }

    @Id
    private String authority;

    public Role(){
    }

    public Role(String name){
        authority = name;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
