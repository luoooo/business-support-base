package com.company.business.support.trace;

import com.company.business.support.config.BusinessSupportProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class TraceIdFilter implements Filter {
    
    private final BusinessSupportProperties properties;
    
    public TraceIdFilter(BusinessSupportProperties properties) {
        this.properties = properties;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!properties.getTraceId().isEnabled()) {
            chain.doFilter(request, response);
            return;
        }
        
        String traceId = getTraceId(request);
        try {
            MDC.put(properties.getTraceId().getMdcKey(), traceId);
            chain.doFilter(request, response);
        } finally {
            MDC.remove(properties.getTraceId().getMdcKey());
        }
    }
    
    private String getTraceId(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            String traceId = ((HttpServletRequest) request).getHeader(properties.getTraceId().getHeaderName());
            if (StringUtils.hasText(traceId)) {
                return traceId;
            }
        }
        return UUID.randomUUID().toString().replace("-", "");
    }
} 