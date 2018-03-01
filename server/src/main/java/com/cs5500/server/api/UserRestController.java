package com.cs5500.server.api;

import com.cs5500.server.WebUtils;
import com.cs5500.server.model.User;
import com.cs5500.server.model.dto.UpdateUserResult;
import com.cs5500.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by mengtao on 3/1/18.
 */
@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @RequestMapping(method=POST, path="/api/users")
    public Object createUser(@RequestBody User user) {
        UpdateUserResult result = userService.createUser(user);
        if (result.isSuccess()) {
            return WebUtils.successMap(result.getUser());
        } else {
            return WebUtils.failedMap(result.getErrorMessages());
        }
    }
}
