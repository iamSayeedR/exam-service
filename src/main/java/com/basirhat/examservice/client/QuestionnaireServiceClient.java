package com.basirhat.examservice.client;


import com.basirhat.questionnaires.model.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "questionnaireServiceClient", url = "${questionnaire-service.url}")
public interface QuestionnaireServiceClient {

    @GetMapping("${questionnaire-service.end-point}")
    ResponseEntity<List<Question>> startExam(@RequestParam String examType);

}
