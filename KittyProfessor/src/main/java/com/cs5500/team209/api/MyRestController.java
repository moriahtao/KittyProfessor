package com.cs5500.team209.api;

import com.cs5500.team209.WebUtils;
import com.cs5500.team209.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by mengtao on 2/20/18.
 *
 * sample apis for validating other RESTful APIs
 */
@RestController
public class MyRestController {

    @Autowired
    private UserRepository repository;

    /**
     * the sample api
     * @param name the name sent from client
     * @return the name and ok status
     */
    @RequestMapping(method=GET, path="/greeting")
    @Secured("Role_Admin")
    public Object greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return WebUtils.successMap(name);
    }

    /**
     * api to get current user in session
     * @param request the request to get current user
     * @return the current username
     */
    @RequestMapping(method=GET, path="/currentuser")
    public Object getCurrentUser(HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        return WebUtils.successMap(name);
    }


}
