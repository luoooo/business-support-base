package com.company.business.support.config;

import com.company.business.support.trace.TraceIdFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "business-support", name = "enabled-modules.trace-id", havingValue = "true")
public class TraceIdConfiguration {
    
    private final BusinessSupportProperties properties;
    
    public TraceIdConfiguration(BusinessSupportProperties properties) {
        this.properties = properties;
    }
    
    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilter() {
        FilterRegistrationBean<TraceIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceIdFilter(properties));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
} 