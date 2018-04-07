package com.cs5500.team209.service;

import com.cs5500.team209.model.Role;
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
     * @param user the user to be deleted
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
    /**
     * validate user having the required fields
     * @param user the user to be validated
     * @return the error messages if any
     */
    private List<String> validateUser(User user) {
        List<String> errorMessages = new ArrayList<>();
        checkNull(errorMessages, user.getPassword(), "Password");
        checkNull(errorMessages, user.getUsername(), "Username");
        return errorMessages;
    }

    /**
     * check if fields are null
     * @param errorMessages error msg
     * @param value the value to be checked
     * @param fieldName the field to be checked
     */
    private void checkNull(List<String> errorMessages, String value, String fieldName) {
        if (StringUtils.isEmpty(value)) {
            errorMessages.add(fieldName + " can't be null.");
        }
    }
}
