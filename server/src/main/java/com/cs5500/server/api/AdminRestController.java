package com.cs5500.server.api;

import com.cs5500.server.WebUtils;
import com.cs5500.server.model.User;
import com.cs5500.server.model.dto.FetchUserResult;
import com.cs5500.server.model.dto.UpdateUserResult;
import com.cs5500.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by mengtao on 2/27/18.
 */
@RestController
public class AdminRestController {

    @Autowired
    UserService userService;

   // @Autowired
    //private SecurityService securityService;


    @RequestMapping(method=GET, path="/admin/user/{username}")
    public Object getUser(@PathVariable("username") String username) {
        FetchUserResult result = userService.getUserByUsername(username);
        if (result != null)
            return WebUtils.successMap(result.getUser());
        else
            return WebUtils.failedMap("User not found.");
    }

    @RequestMapping(method=POST, path="/admin/users")
    public Object createUser(@RequestBody User user) {
        UpdateUserResult result = userService.createUser(user);
        if (result.isSuccess()) {
            return WebUtils.createSuccessMap(result.getUser());
        } else {
            return WebUtils.failedMap(result.getErrorMessages());
        }
    }

    @RequestMapping(method=PUT, path="/admin/user/{username}")
    public Object updateUser(@PathVariable("username") String username, @RequestBody User user) {
        FetchUserResult result = userService.getUserByUsername(username);
        if (result != null){
            user.setId(result.getUser().getId());
            UpdateUserResult newUser = userService.createUser(user);
            if (newUser != null){
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
