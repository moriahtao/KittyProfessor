package com.cs5500.server.service;

import com.cs5500.server.model.Role;
import com.cs5500.server.model.User;
import com.cs5500.server.model.dto.FetchUserResult;
import com.cs5500.server.model.dto.UpdateUserResult;
import com.cs5500.server.repository.RoleRepository;
import com.cs5500.server.repository.UserRepository;
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
        List<String> errorMessages = validateUser(user);
        if (errorMessages == null || errorMessages.size() == 0) {
            if (user.getRoles() == null || user.getRoles().size() == 0) {
                List<Role> roles = new ArrayList<>();
                roles.add(new Role("ROLE_USER"));
                user.setRoles(roles);
            }
            User createdUser = userRepository.save(user);
            return new UpdateUserResult(createdUser);
        } else {
            return new UpdateUserResult(errorMessages);
        }
    }

    /**
     * delete user
     * @param user the user to be deleted
     */
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    /**
     * get user by username
     * @param username the username of the user to be got
     * @return
     */
    public FetchUserResult getUserByUsername(String username) {
        return new FetchUserResult(userRepository.findByUsername(username));
    }

    private List<String> validateUser(User user) {
        List<String> errorMessages = new ArrayList<>();
        checkNull(errorMessages, user.getPassword(), "Password");
        checkNull(errorMessages, user.getUsername(), "Username");
        return errorMessages;
    }

    private void checkNull(List<String> errorMessages, String value, String fieldName) {
        if (StringUtils.isEmpty(value)) {
            errorMessages.add(fieldName + " can't be null.");
        }
    }
}
