package com.company.business.support.aspect;

import com.company.business.support.annotation.Loggable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggableAspect {

    @Around("@annotation(com.company.business.support.annotation.Loggable)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = point.proceed();
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            Loggable loggable = method.getAnnotation(Loggable.class);
            
            StringBuilder logMessage = new StringBuilder();
            logMessage.append("Method ").append(method.getName()).append(" execution");
            
            if (loggable.printArgs()) {
                logMessage.append(" - Args: ").append(Arrays.toString(point.getArgs()));
            }
            
            if (loggable.printResult()) {
                logMessage.append(" - Result: ").append(result);
            }
            
            if (loggable.printExecutionTime()) {
                logMessage.append(" - Time: ").append(endTime - startTime).append("ms");
            }
            
            switch (loggable.level().toUpperCase()) {
                case "DEBUG":
                    log.debug(logMessage.toString());
                    break;
                case "WARN":
                    log.warn(logMessage.toString());
                    break;
                case "ERROR":
                    log.error(logMessage.toString());
                    break;
                default:
                    log.info(logMessage.toString());
            }
        }
    }
} 