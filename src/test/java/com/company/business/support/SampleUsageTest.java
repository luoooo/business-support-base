package com.company.business.support;

import com.company.business.support.annotation.DataSource;
import com.company.business.support.annotation.Loggable;
import com.company.business.support.annotation.MqListener;
import com.company.business.support.annotation.RedisLock;
import com.company.business.support.exception.BusinessException;
import com.company.business.support.mq.MqTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class SampleUsageTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MqTemplate mqTemplate;

    @Test
    @Loggable(level = "INFO", printArgs = true, printResult = true)
    public void testLoggable() {
        // Method execution will be logged with arguments and result
        String result = "Test result";
        System.out.println(result);
    }

    @Test
    @RedisLock(key = "test:lock:#{orderId}", expire = 30)
    public void testRedisLock(String orderId) {
        // Method execution will be protected by Redis lock
        System.out.println("Processing order: " + orderId);
    }

    @Test
    @DataSource("secondary")
    public void testMultiDatasource() {
        // Query will be executed on secondary datasource
        List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM test_table");
        System.out.println("Results: " + results);
    }

    @Test
    public void testMqSupport() {
        // Send message to queue
        mqTemplate.send("test.exchange", "test.queue", "Test message");
    }

    @Test
    @MqListener(queue = "test.queue")
    public void testMqListener(String message) {
        // Process message from queue
        System.out.println("Received message: " + message);
    }

    @Test
    public void testGlobalException() {
        // This will be caught by global exception handler
        throw new BusinessException("400", "Test business exception");
    }
} 