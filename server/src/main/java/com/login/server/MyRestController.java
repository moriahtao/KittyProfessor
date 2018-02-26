package com.login.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by mengtao on 2/20/18.
 */
@RestController
public class MyRestController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(method=GET, path="/greeting")
    @Secured("Role_Admin")
    public Object greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return WebUtils.successMap(name);
    }

    @RequestMapping(method=GET, path="/currentuser")
    public Object getCurrentUser(HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        return WebUtils.successMap(name);
    }

    @RequestMapping(method=GET, path="/admin/user/{username}")
    public Object getUser(@PathVariable("username") String username) {
        User user = repository.findByUsername(username);
        if (user != null)
            return WebUtils.successMap(user);
        else
            return WebUtils.failedMap("User not found.");
    }

    @RequestMapping(method=POST, path="/admin/users")
    public Object createUser(@RequestBody User user) {
        User u = repository.save(user);
        if (u != null)
            return WebUtils.successMap("User created successfully.");
        else
            return WebUtils.failedMap("Error occurred when creating user.");
    }

    @RequestMapping(method=PUT, path="/admin/user/{username}")
    public Object updateUser(@PathVariable("username") String username, @RequestBody User user) {
        User oldUser = repository.findByUsername(username);
        if (oldUser != null){
            user.setId(oldUser.getId());
            User result = repository.save(user);
            if (result != null){
                return WebUtils.successMap("User updated successfully.");
            }
            else{
                return WebUtils.failedMap("Error occurred when updating user.");
            }
        }
        else{
            return WebUtils.failedMap("User not found.");
        }

    }

    @RequestMapping(method=DELETE, path="/admin/user/{username}")
    public Object deleteUser(@PathVariable("username") String username, @RequestBody User user) {
        User oldUser = repository.findByUsername(username);
        if (oldUser != null){
            repository.delete(user);
            return WebUtils.successMap("User deleted successfully.");
        }
        else{
            return WebUtils.failedMap("User not found.");
        }

    }
}
