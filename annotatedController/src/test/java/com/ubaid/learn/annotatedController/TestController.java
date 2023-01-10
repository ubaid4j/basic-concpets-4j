package com.ubaid.learn.annotatedController;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
@WebAppConfiguration
public class TestController {
    
    private MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext wac;
    
    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    
    @Test
    void testHelloWorld() throws Exception {
        RequestBuilder getHelloWorld = MockMvcRequestBuilders
            .get("/");
        
        mockMvc
            .perform(getHelloWorld)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$" ).value("Hello World"));

    }

    @Test
    void testHead() throws Exception {
        RequestBuilder headHelloWorld = MockMvcRequestBuilders
            .head("/");

        mockMvc
            .perform(headHelloWorld)
            .andDo(print())
            .andExpect(status().isOk());

    }

    @Test
    void testOptions() throws Exception {
        RequestBuilder optionsHelloWorld = MockMvcRequestBuilders
            .options("/");

        mockMvc
            .perform(optionsHelloWorld)
            .andDo(print())
            .andExpect(status().isOk());

    }
    
    
    @RepeatedTest(value = 5)
    void testServiceInitializationCounter() throws Exception{
        RequestBuilder getInitializationCounter = MockMvcRequestBuilders
            .get("/initialization-counter");

        mockMvc
            .perform(getInitializationCounter)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$" ).value(1));
    }
    
    @Test
    void testPersonsEndPoint() throws Exception {
        RequestBuilder getInitializationCounter = MockMvcRequestBuilders
            .get("/persons")
            .characterEncoding("UTF-8");

        mockMvc
            .perform(getInitializationCounter)
            .andDo(print())
            .andExpect(status().isOk());
    }


}
