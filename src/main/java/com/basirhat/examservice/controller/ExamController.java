package com.basirhat.examservice.controller;

import com.basirhat.examservice.model.Question;
import com.basirhat.examservice.service.QuestionnaireService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ExamController {

    private final QuestionnaireService questionnaireService;

    @GetMapping("/start")
    public ResponseEntity<List<Question>> startExam(@NotBlank @RequestParam String examType) {
        log.info("examType selected - {} ", examType);

        if (examType.equals("null")) {
            throw new IllegalArgumentException("examType cannot be null");
        }

        return ResponseEntity.ok(questionnaireService.getQuestions(examType));
    }

    @PostMapping("/end")
    public ResponseEntity<Void> endExam() {
        log.info("end exam");
        return ResponseEntity.ok().build();
    }
}
