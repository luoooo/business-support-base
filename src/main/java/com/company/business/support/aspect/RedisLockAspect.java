package com.company.business.support.aspect;

import com.company.business.support.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class RedisLockAspect {
    
    private final StringRedisTemplate redisTemplate;
    private final ExpressionParser parser = new SpelExpressionParser();
    
    public RedisLockAspect(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @Around("@annotation(redisLock)")
    public Object around(ProceedingJoinPoint point, RedisLock redisLock) throws Throwable {
        String key = parseKey(point, redisLock.key());
        boolean locked = false;
        try {
            locked = redisTemplate.opsForValue().setIfAbsent(key, "1", redisLock.timeout(), TimeUnit.MILLISECONDS);
            if (!locked) {
                throw new RuntimeException("Failed to acquire lock: " + key);
            }
            return point.proceed();
        } finally {
            if (locked) {
                redisTemplate.delete(key);
            }
        }
    }
    
    private String parseKey(ProceedingJoinPoint point, String keyExpression) {
        // 支持通过参数名访问 SpEL，如 #key
        org.aspectj.lang.reflect.MethodSignature signature = (org.aspectj.lang.reflect.MethodSignature) point.getSignature();
        String[] paramNames = signature.getParameterNames();
        Object[] args = point.getArgs();
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return parser.parseExpression(keyExpression).getValue(context, String.class);
    }
}