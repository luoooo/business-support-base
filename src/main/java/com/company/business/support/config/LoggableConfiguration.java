package com.company.business.support.config;

import com.company.business.support.aspect.LoggableAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "business-support", name = "enabled-modules.annotation-log", havingValue = "true")
public class LoggableConfiguration {
    
    @Bean
    public LoggableAspect loggableAspect() {
        return new LoggableAspect();
    }
} 