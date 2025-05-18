package com.company.business.support.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {
    String key();
    long expire() default 30;
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    int retryTimes() default 3;
    long retryInterval() default 100;
    long timeout() default 30000;
} 