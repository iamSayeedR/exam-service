package com.basirhat.examservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Getter
@Setter
public class RabbitQueueProperty {
    private String examResultExchangeName;
    private String examResultRoutingKey;
}
