package com.cs5500.server.api;

import com.cs5500.server.Driver;
import com.cs5500.server.model.Role;
import com.cs5500.server.model.User;
import com.cs5500.server.model.dto.FetchUserResult;
import com.cs5500.server.repository.RoleRepository;
import com.cs5500.server.repository.UserRepository;
import com.cs5500.server.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * Created by mengtao on 2/28/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Driver.class)
@WebAppConfiguration
public class AdminRestControllerTest {


    //@Autowired
    private MockMvc mvc;

    @Autowired
    private UserService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setup() throws Exception {
        this.mvc = webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_USER"));
        userRepository.save(new User("alice", "test123"));
        userRepository.save(new User("bob", "test123"));
    }
    // write test cases here
    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
            throws Exception {

        User alex = new User("alex", "1234");

        //given(service.getUserByUsername("alex")).willReturn(new FetchUserResult(alex));

        //when(service.getUserByUsername("alex")).thenReturn(new FetchUserResult(alex));

        this.mvc.perform(get("/admin/user/alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
        ;
                //.andExpect(content().string(containsString("Hello Mock")));

    }


}
