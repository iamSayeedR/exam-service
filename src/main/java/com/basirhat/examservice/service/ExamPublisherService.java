package com.basirhat.examservice.service;

import com.basirhat.examservice.config.RabbitQueueProperty;
import com.basirhat.questionnaires.model.AnswerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class ExamPublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitQueueProperty rabbitQueueProperty;

    public void sendMessage(AnswerRequest answerRequest) {

        log.info("");
        rabbitTemplate.convertAndSend(
                rabbitQueueProperty.getExamResultExchangeName(),
                rabbitQueueProperty.getExamResultRoutingKey(),
                answerRequest);

    }

}
