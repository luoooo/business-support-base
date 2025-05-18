package com.company.business.support.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MqListener {
    String queue();
    String exchange() default "";
    String routingKey() default "";
    boolean autoAck() default true;
    int prefetchCount() default 1;
} 