package com.company.business.support.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqTemplate {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    public MqTemplate(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }
    
    public void send(String exchange, String routingKey, Object message) {
        try {
            String messageBody = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(exchange, routingKey, messageBody);
            log.info("Message sent to exchange: {}, routing key: {}", exchange, routingKey);
        } catch (Exception e) {
            log.error("Failed to send message: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send message", e);
        }
    }
} 