package com.cs5500.team209.api;

import com.cs5500.team209.WebUtils;
import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.FetchUserResult;
import com.cs5500.team209.model.dto.UpdateUserResult;
import com.cs5500.team209.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by mengtao on 2/27/18.
 *
 * class to implement the REST APIs with
 * admin authority
 */
@RestController
public class AdminRestController {

    @Autowired
    UserService userService;


    /**
     * rest api for get user by username
     * @param username the username for query
     * @return response with user and http status
     */
    @RequestMapping(method=GET, path="/admin/user/{username}")
    public Object getUser(@PathVariable("username") String username) {
        FetchUserResult result = userService.getUserByUsername(username);
        if (result != null)
            return WebUtils.successMap(result.getUser());
        else
            return WebUtils.failedMap("User not found.");
    }

    /**
     * rest api for creating a user
     * @param user the User object to be created
     * @return response with user and http status
     */
    @RequestMapping(method=POST, path="/admin/users")
    public Object createUser(@RequestBody User user) {
        UpdateUserResult result = userService.createUser(user);
        if (result.isSuccess()) {
            return WebUtils.createSuccessMap(result.getUser());
        } else {
            return WebUtils.failedMap(result.getErrorMessages());
        }
    }

    /**
     * rest api for updating a user by username
     * @param username the user to be updated
     * @param user the updated user
     * @return the user updated when success
     */
    @RequestMapping(method=PUT, path="/admin/user/{username}")
    public Object updateUser(@PathVariable("username") String username, @RequestBody User user) {
        FetchUserResult result = userService.getUserByUsername(username);
        if (result != null){

            UpdateUserResult newUser = userService.createUser(user);
            if (newUser.isSuccess()){
                return WebUtils.successMap(newUser.getUser());
            }
            else{
                return WebUtils.failedMap(newUser.getErrorMessages());
            }
        }
        else{
            return WebUtils.failedMap("User not found.");
        }
    }

    /**
     * delete the user by username
     * @param username the username of the user to be deleted
     * @param user the user to be deleted
     * @return success msg when success
     */
    @RequestMapping(method=DELETE, path="/admin/user/{username}")
    public Object deleteUser(@PathVariable("username") String username, @RequestBody User user) {
        FetchUserResult oldUser = userService.getUserByUsername(username);
        if (oldUser != null){
            userService.deleteUser(user);
            return WebUtils.successMap("User deleted successfully.");
        }
        else{
            return WebUtils.failedMap("User not found.");
        }

    }
}
