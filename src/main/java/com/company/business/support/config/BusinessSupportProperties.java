package com.company.business.support.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "business-support")
public class BusinessSupportProperties {
    private EnabledModules enabledModules = new EnabledModules();
    private GlobalException globalException = new GlobalException();
    private TraceId traceId = new TraceId();

    @Data
    public static class EnabledModules {
        private boolean globalException = true;
        private boolean annotationLog = true;
        private boolean traceId = true;
        private boolean redisLock = true;
        private boolean multiDatasource = true;
        private boolean mqSupport = true;
    }

    @Data
    public static class GlobalException {
        private String errorCodePrefix = "BUSINESS_ERROR_";
        private boolean printStackTrace = false;
    }

    @Data
    public static class TraceId {
        private String headerName = "X-Trace-Id";
        private String mdcKey = "traceId";
        private boolean enabled = true;
    }
} 