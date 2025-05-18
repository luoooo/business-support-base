package com.company.business.support.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Configuration
@ConditionalOnProperty(prefix = "business-support", name = "enabled-modules.multi-datasource", havingValue = "true")
public class MultiDatasourceConfiguration extends AbstractRoutingDataSource {
    
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getDataSource();
    }
} 