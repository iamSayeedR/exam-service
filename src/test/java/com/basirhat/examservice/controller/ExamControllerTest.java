package com.basirhat.examservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExamControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    void shouldTestGetAndReturn200WhenControllerIsCalled() throws Exception {

        String type = "Java 17";
        mockMvc.perform(
                        get("/v1/start?examType=" + type)
                                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

    }


}