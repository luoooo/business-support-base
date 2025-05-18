package com.company.business.support.config;

import com.company.business.support.exception.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "business-support", name = "enabled-modules.global-exception", havingValue = "true")
public class GlobalExceptionConfiguration {
    
    private final BusinessSupportProperties properties;
    
    public GlobalExceptionConfiguration(BusinessSupportProperties properties) {
        this.properties = properties;
    }
    
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler(properties);
    }
} 