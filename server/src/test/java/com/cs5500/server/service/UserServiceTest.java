package com.cs5500.server.service;

import com.cs5500.server.Driver;
import com.cs5500.server.model.User;
import com.cs5500.server.model.dto.FetchUserResult;
import com.cs5500.server.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by mengtao on 2/28/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Driver.class)
public class UserServiceTest {
    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User alex = new User("alex", "1234");
        userRepository.save(alex);
        Mockito.when(userRepository.findByUsername(alex.getUsername())).thenReturn(alex);
    }

    @Test
    public void whenValidName_thenUserShouldBeFound() {
        String name = "alex";
        FetchUserResult found = userService.getUserByUsername(name);

        assertThat(found.getUser().getUsername()).isEqualTo(name);
    }
}