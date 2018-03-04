package com.cs5500.server.security;

import com.cs5500.server.security.MySavedRequestAwareAuthenticationSuccessHandler;
import com.cs5500.server.security.RestAuthenticationEntryPoint;
import com.cs5500.server.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * a security solution for Java EE applications
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private MyUserDetailService userDetailsService;

    /**
     * configure which users need to be authenticated
     * @param http allows configuring web based security for specific http requests
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/currentuser").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/greeting").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/api/users").permitAll()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider()).inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
    }


    @Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler(){
        return new MySavedRequestAwareAuthenticationSuccessHandler(new MappingJackson2HttpMessageConverter());
    }
    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }

    /**
     * An AuthenticationProvider implementation that
     * retrieves user details from a UserDetailsService.
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    /**
     * add a header to all response
     * tell browser to trust the client
     * @return Configure cross origin requests processing
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}
