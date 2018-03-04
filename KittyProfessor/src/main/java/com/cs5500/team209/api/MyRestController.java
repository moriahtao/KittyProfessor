package com.cs5500.team209.api;

import com.cs5500.team209.repository.UserRepository;
import com.cs5500.team209.WebUtils;
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


}
