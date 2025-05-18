package com.company.business.support.config;

import com.company.business.support.aspect.RedisLockAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@ConditionalOnProperty(prefix = "business-support", name = "enabled-modules.redis-lock", havingValue = "true")
public class RedisLockConfiguration {
    
    private final StringRedisTemplate redisTemplate;
    
    public RedisLockConfiguration(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @Bean
    public RedisLockAspect redisLockAspect() {
        return new RedisLockAspect(redisTemplate);
    }
} 