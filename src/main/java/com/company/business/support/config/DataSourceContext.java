package com.company.business.support.config;

import java.util.concurrent.atomic.AtomicReference;

public class DataSourceContext {
    private static final AtomicReference<String> CURRENT = new AtomicReference<>("primary");
    
    public static void setDataSource(String dataSource) {
        CURRENT.set(dataSource);
    }
    
    public static String getDataSource() {
        return CURRENT.get();
    }
    
    public static void clear() {
        CURRENT.set("primary");
    }
} 