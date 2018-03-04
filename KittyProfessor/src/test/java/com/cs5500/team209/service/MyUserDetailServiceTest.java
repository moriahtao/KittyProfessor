package com.cs5500.team209.service;

import com.cs5500.team209.model.User;
import com.cs5500.team209.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 3/4/18.
 */
@RunWith(SpringRunner.class)
public class MyUserDetailServiceTest {
    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public MyUserDetailService myUserDetailService() {
            return new MyUserDetailService();
        }
    }

    @Autowired
    private MyUserDetailService myUserDetailService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User alex = new User("alex", "1234");
        userRepository.save(alex);
        Mockito.when(userRepository.findByUsername(alex.getUsername())).thenReturn(alex);
    }

    @Test
    public void testMyUserDetailService() {
        String name = "alex";
        UserDetails found = myUserDetailService.loadUserByUsername(name);
        assertEquals(name, found.getUsername());
    }

}