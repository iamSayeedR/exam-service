package com.basirhat.examservice.controller;


import com.basirhat.examservice.service.QuestionnaireService;
import com.basirhat.questionnaires.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionnaireService questionnaireService;

    @Test
    void shouldTestGetAndReturn200WhenControllerIsCalledSuccessfully() throws Exception {
        String type = "Java 17";

        when(questionnaireService.getQuestions(type)).thenReturn(List.of(Question.builder().build()));

        mockMvc.perform(
                        get("/v1/start?examType=%s".formatted(type))
                                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        verify(questionnaireService).getQuestions(type);

    }
    @Test
    void shouldReturn400WhenExamTypeIsEmpty() throws Exception {
        String type = "";
        mockMvc.perform(
                        get("/v1/start?examType=%s".formatted(type))
                                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldReturn400WhenExamTypeIsNull() throws Exception {
        String type = null;
        mockMvc.perform(
                        get("/v1/start?examType=%s".formatted(type))
                                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();

        when(questionnaireService.getQuestions(type)).thenReturn(List.of(Question.builder().build()));

        verify(questionnaireService, never()).getQuestions(type);
    }

    @Test
    void shouldReturn400WhenExamTypeIsNotPassed() throws Exception {
        mockMvc.perform(
                        get("/v1/start?examType=")
                                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}