package com.company.business.support.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Loggable {
    String level() default "INFO";
    boolean printArgs() default true;
    boolean printResult() default true;
    boolean printExecutionTime() default true;
} 