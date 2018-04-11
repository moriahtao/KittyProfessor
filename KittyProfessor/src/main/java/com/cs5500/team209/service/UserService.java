package com.cs5500.team209.service;

import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.FetchUserResult;
import com.cs5500.team209.model.dto.UpdateUserResult;
import com.cs5500.team209.repository.RoleRepository;
import com.cs5500.team209.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtao on 2/27/18.
 *
 * KittyProfessor defined UserService
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    /**
     * create user
     * @param user the user obj to be created
     * @return the Spring Security
     */
    public UpdateUserResult createUser(User user) {
            User createdUser = userRepository.save(user);
            return new UpdateUserResult(createdUser);
    }

    /**
     * delete user
     * @param username the user to be deleted
     */
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }


    /**
     * get user by username
     * @param username the username of the user to be got
     * @return FetchUserResult
     */
    public FetchUserResult getUserByUsername(String username) {
        return new FetchUserResult(userRepository.findByUsername(username));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
