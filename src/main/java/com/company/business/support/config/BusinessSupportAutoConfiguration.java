package com.company.business.support.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(BusinessSupportProperties.class)
@Import({
    GlobalExceptionConfiguration.class,
    LoggableConfiguration.class,
    TraceIdConfiguration.class,
    RedisLockConfiguration.class,
    MultiDatasourceConfiguration.class,
    MqSupportConfiguration.class
})
public class BusinessSupportAutoConfiguration {
} 