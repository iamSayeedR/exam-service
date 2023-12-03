package com.basirhat.examservice.service;

import com.basirhat.examservice.client.QuestionnaireServiceClient;
import com.basirhat.examservice.model.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionnaireService {

    private final QuestionnaireServiceClient questionnaireServiceClient;

    /**
     * This method generates questions for the given type
     * @param type
     * @return
     */

    public List<Question> getQuestions(String type) {

        log.info("getting list of questions");

        ResponseEntity<List<Question>> listResponseEntity = questionnaireServiceClient.startExam(type);

        if (listResponseEntity.getStatusCode().is2xxSuccessful()) {
            return listResponseEntity.getBody();
        } else {
            throw new RestClientException("Could not able to call Questionnaire Service Client.");
        }

    }


}
