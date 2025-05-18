package com.company.business.support.aspect;

import com.company.business.support.annotation.DataSource;
import com.company.business.support.config.DataSourceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DataSourceAspect {

    @Around("@annotation(com.company.business.support.annotation.DataSource)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSource dataSource = method.getAnnotation(DataSource.class);

        String previousDataSource = DataSourceContext.getDataSource();
        try {
            DataSourceContext.setDataSource(dataSource.value());
            return point.proceed();
        } finally {
            if (previousDataSource != null) {
                DataSourceContext.setDataSource(previousDataSource);
            } else {
                DataSourceContext.clear();
            }
        }
    }
} 