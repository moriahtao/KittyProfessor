package com.cs5500.team209.service;

import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.MyUserPrinciple;
import com.cs5500.team209.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by mengtao on 2/25/18.
 *
 * Spring Security requires to implement UserDetailsService
 * as extra User service.
 *
 * MyUserDetailService serves for this purpose
 * to separate from our UserService
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrinciple(user);
    }
}
