package com.basirhat.examservice.controller;


import com.basirhat.examservice.service.ExamPublisherService;
import com.basirhat.examservice.service.QuestionnaireService;
import com.basirhat.questionnaires.model.AnswerRequest;
import com.basirhat.questionnaires.model.Question;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ExamController {

    private final QuestionnaireService questionnaireService;

    private final ExamPublisherService examPublisherService;

    @GetMapping("/start")
    public ResponseEntity<List<Question>> startExam(@NotBlank @RequestParam String examType) {
        log.info("examType selected - {} ", examType);

        if (examType.equals("null")) {
            throw new IllegalArgumentException("examType cannot be null");
        }

        return ResponseEntity.ok(questionnaireService.getQuestions(examType));
    }

    @PostMapping("/end")
    public ResponseEntity<Void> endExam(@RequestBody AnswerRequest answerRequest) {
        log.info("end exam");
        examPublisherService.sendMessage(answerRequest);
        return ResponseEntity.accepted().build();
    }
}

//create a new spring boot service for this end
// v1/questions/end
//<answer> "type" : java 17, "qid" : 1 ,"questions":"ghj","answers" ["A","B"]
//