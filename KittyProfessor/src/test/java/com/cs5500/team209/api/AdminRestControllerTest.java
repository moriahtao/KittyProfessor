package com.cs5500.team209.api;

import com.cs5500.team209.Driver;
import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.FetchUserResult;
import com.cs5500.team209.model.dto.UpdateUserResult;
import com.cs5500.team209.repository.UserRepository;
import com.cs5500.team209.service.UserService;
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
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * Created by mengtao on 2/28/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Driver.class)
@WebAppConfiguration
public class AdminRestControllerTest {

    private MockMvc mvc;

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
        //roleRepository.deleteAll();
        User joe = new User("joe", "1234");
        User andy = new User("andy", "1234");
        //roleRepository.save(new Role("ROLE_ADMIN"));
        //roleRepository.save(new Role("ROLE_USER"));
        userRepository.save(joe);
        userRepository.save(andy);
        Mockito.when(userRepository.findByUsername(joe.getUsername())).thenReturn(joe);
        Mockito.when(userRepository.findByUsername(andy.getUsername())).thenReturn(andy);
    }
    // write test cases here
    /*@Test
    public void givenUserByUserName_thenReturnJsonArray()
            throws Exception {
        User joe = new User("joe", "1234");
        User andy = new User("andy", "1234");

        this.mvc.perform(get("/admin/user/joe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.username", is(joe.getUsername())))
                .andExpect(jsonPath("$.data.password", is(nullValue())));
        //.andExpect(content().string(containsString("Hello Mock")));

    }*/

    @Autowired
    ObjectMapper objectMapper;
    @Test public void givenUser_thenReturnJsonArray() throws Exception {
        User mockUser = new User("joe", "1234");

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(new UpdateUserResult(mockUser));

        String exampleUserJson = "{\"username\":\"joe\",\"password\":\"1234\"}";

        // Send user as body to
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/users")
                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test public void givenUserByUsername_thenReturnJsonArray() throws Exception {
        User mockUser = new User("joe", "1234");

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(userService.getUserByUsername(Mockito.anyString())).thenReturn(new FetchUserResult(mockUser));

        String exampleUserJson = "{\"username\":\"joe\",\"password\":\"1234\"}";

        // Send user as body to
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/user/joe");

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test public void givenUpdatedUser_thenReturnJsonArray() throws Exception {
        User mockUser = new User("joe", "1234");

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(userService.getUserByUsername(Mockito.anyString())).thenReturn(new FetchUserResult(mockUser));
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(new UpdateUserResult(mockUser));

        String exampleUserJson = "{\"username\":\"joe\",\"password\":\"1234\"}";

        // Send user as body to
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/admin/user/joe")
                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"data\":{\"id\":null,\"username\":\"joe\",\"password\":null,\"firstName\":null,\"lastName\":null,\"roles\":[{\"authority\":\"ROLE_USER\"}],\"joinAs\":null,\"university\":null,\"email\":null},\"success\":true}", response.getContentAsString());

    }

    @Test public void deleteUser_thenReturnJsonArray() throws Exception {
        User mockUser = new User("joe", "1234");

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(userService.getUserByUsername(Mockito.anyString())).thenReturn(new FetchUserResult(mockUser));


        String exampleUserJson = "{\"username\":\"joe\",\"password\":\"1234\"}";

        // Send user as body to
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/admin/user/joe")
                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"data\":\"User deleted successfully.\",\"success\":true}", response.getContentAsString());

    }
    /*@Test public void givenUpdatedUser_thenReturnJsonArray() throws Exception {
        User mockUser = new User("joe", "1234");

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(
                userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);

        String exampleUserJson = "{\"username\":\"joe\",\"password\":\"1234\"}";

        // Send user as body to
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/users")
                .accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }*/


}
