package com.cs5500.team209.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mengtao on 2/25/18.
 *
 * the entry point when receiving a request
 */
@Component( "restAuthenticationEntryPoint" )
public class RestAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    /**
     * the Spring security request evaluation
     * @param request the request to be evaluated
     * @param response the server response
     * @param authException exception
     * @throws IOException
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
    }
}