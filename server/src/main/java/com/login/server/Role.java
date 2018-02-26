package com.login.server;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by mengtao on 2/26/18.
 */
public class Role implements GrantedAuthority {

    @Id
    private String authority;

    public Role(){
    }

    public Role(String name){
        authority = name;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
