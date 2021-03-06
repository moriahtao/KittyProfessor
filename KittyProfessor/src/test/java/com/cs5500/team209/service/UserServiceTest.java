package com.cs5500.team209.service;

import com.cs5500.team209.Driver;
import com.cs5500.team209.model.Role;
import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.FetchUserResult;
import com.cs5500.team209.model.dto.UpdateUserResult;
import com.cs5500.team209.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by mengtao on 2/28/18.
 *
 * Tests for UserService
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

    /**
     * setup mock db before testing
     */
    @Before
    public void setUp() {
        List<User> userList = new ArrayList<>();
        User alex = new User("alex", "1234", "admin");
        User ann = new User("ann", "1234", "student", "neu", "example@gmail");
        userList.add(alex);
        userList.add(ann);
        userRepository.save(alex);
        Mockito.when(userRepository.findByUsername(alex.getUsername())).thenReturn(alex);
        Mockito.when(userRepository.findByUsername("joe")).thenReturn(null);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(ann);
        Mockito.when(userRepository.findAll()).thenReturn(userList);

    }

    /**
     * should return user after
     * successfully get user by username
     */
    @Test
    public void whenValidName_thenUserShouldBeFound() {
        String name = "alex";
        FetchUserResult found = userService.getUserByUsername(name);

        assertThat(found.getUser().getUsername()).isEqualTo(name);
    }

    /**
     * should return user after successfully create user
     */
    @Test
    public void whenValidUser_thenUserShouldBeFound() {
        User user = new User("ann", "1234", "student", "neu", "example@gmail");
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role2);
        user.setRoles(roleList);
        UpdateUserResult found = userService.createUser(user);
        assertEquals("ann", found.getUser().getUsername());
        user.setRoles(new ArrayList<Role>());
        found = userService.createUser(user);
        assertEquals("ann", found.getUser().getUsername());
    }

    /**
     * delete user
     */
    @Test
    public void whenValidUser_thenUserShouldBeDeleted() {
        User user = new User("ann", "1234", "student", "neu", "example@gmail");
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role2);
        user.setRoles(roleList);
        userService.deleteUser(user.getUsername());
    }

    @Test
    public void whenFindAll_thenUserListShouldBeFound() {
        List<User> userList = userService.getAllUsers();
        assertEquals(2, userList.size());
    }

}