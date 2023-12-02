package com.basirhat.examservice.controller;

import com.basirhat.examservice.model.Question;
import com.basirhat.examservice.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
public class ExamController {

    private final QuestionnaireService questionnaireService;

    @GetMapping("/start")
    public ResponseEntity<List<Question>> startExam(@RequestParam String examType) {

        log.info("examType selected - {} ", examType);

        return ResponseEntity.ok(questionnaireService.getQuestions(examType));

    }

    @PostMapping("/end")
    public ResponseEntity<Void> endExam() {

        return ResponseEntity.ok().build();
    }
}
