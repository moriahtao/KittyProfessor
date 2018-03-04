package com.cs5500.team209.api;

import com.cs5500.team209.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by mengtao on 3/3/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Driver.class)
@WebAppConfiguration
public class MyRestControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private HttpServletRequest request;

    @Before
    public void setup() throws Exception {
        this.mvc = webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    ObjectMapper objectMapper;
    @Test
    public void testGreetings() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/greeting");

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

   /* @Test
    public void testGetCurrentUser() throws Exception {
        User joe = new User("joe", "1234");
        Mockito.when(request.getUserPrincipal()).thenReturn(new Principal() {
            @Override
            public String getName() {
                return "joe";
            }
        });

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/currentuser");

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }*/


}