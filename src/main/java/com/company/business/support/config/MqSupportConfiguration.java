package com.company.business.support.config;

import com.company.business.support.aspect.MqListenerAspect;
import com.company.business.support.mq.MqTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "business-support", name = "enabled-modules.mq-support", havingValue = "true")
public class MqSupportConfiguration {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    public MqSupportConfiguration(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }
    
    @Bean
    public MqTemplate mqTemplate() {
        return new MqTemplate(rabbitTemplate, objectMapper);
    }
    
    @Bean
    public MqListenerAspect mqListenerAspect() {
        return new MqListenerAspect(objectMapper, rabbitTemplate);
    }
} 