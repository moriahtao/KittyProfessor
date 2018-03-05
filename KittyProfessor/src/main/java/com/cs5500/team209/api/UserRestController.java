package com.cs5500.team209.api;

import com.cs5500.team209.WebUtils;
import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.UpdateUserResult;
import com.cs5500.team209.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by mengtao on 3/1/18.
 *
 * class to implement the REST APIs with
 * user authority
 */
@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    /**
     * rest api for creating a user
     * @param user the User object to be created
     * @return response with user and http status
     */
    @RequestMapping(method=POST, path="/api/users")
    public Object createUser(@RequestBody User user) {
        UpdateUserResult result = userService.createUser(user);
        if (result.isSuccess()) {
            return WebUtils.createSuccessMap(result.getUser());
        } else {
            return WebUtils.failedMap(result.getErrorMessages());
        }
    }
}
