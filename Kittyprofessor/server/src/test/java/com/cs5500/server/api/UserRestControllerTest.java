package com.cs5500.server.api;

import com.cs5500.server.Driver;
import com.cs5500.server.model.User;
import com.cs5500.server.model.dto.UpdateUserResult;
import com.cs5500.server.repository.UserRepository;
import com.cs5500.server.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by mengtao on 3/2/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Driver.class)
@WebAppConfiguration
public class UserRestControllerTest {
    private MockMvc mvc;

    //@Autowired
    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() throws Exception {
        this.mvc = webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();
        User joe = new User("joe", "1234");
        User andy = new User("andy", "1234");
        userRepository.save(joe);
        userRepository.save(andy);
        Mockito.when(userRepository.findByUsername(joe.getUsername())).thenReturn(joe);
        Mockito.when(userRepository.findByUsername(andy.getUsername())).thenReturn(andy);
    }

    @Autowired
    ObjectMapper objectMapper;
    @Test
    public void givenUser_thenReturnJsonArray() throws Exception {
        User mockUser = new User("joe", "1234");

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(new UpdateUserResult(mockUser));

        String exampleUserJson = "{\"username\":\"joe\",\"password\":\"1234\"}";

        // Send user as body to
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/users")
                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}