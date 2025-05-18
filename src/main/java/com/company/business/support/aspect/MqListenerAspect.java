package com.company.business.support.aspect;

import com.company.business.support.annotation.MqListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
@Slf4j
public class MqListenerAspect {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public MqListenerAspect(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Around("@annotation(com.company.business.support.annotation.MqListener)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        MqListener mqListener = method.getAnnotation(MqListener.class);

        Object[] args = point.getArgs();
        if (args.length > 0 && args[0] instanceof Message) {
            Message message = (Message) args[0];
            String messageBody = new String(message.getBody());
            
            try {
                Parameter parameter = method.getParameters()[0];
                Object convertedMessage = objectMapper.readValue(messageBody, parameter.getType());
                args[0] = convertedMessage;
                
                return point.proceed(args);
            } catch (Exception e) {
                log.error("Failed to process message from queue {}: {}", mqListener.queue(), messageBody, e);
                throw e;
            }
        }
        
        return point.proceed();
    }
} 