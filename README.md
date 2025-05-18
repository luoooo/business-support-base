# Business Support Core

A comprehensive business support component that provides common infrastructure capabilities for business development teams.

## Features

- Global exception handling and logging
- Method-level logging with annotations
- Log trace tracking (TraceID)
- Distributed Redis lock
- Dynamic multi-datasource switching
- Unified MQ message support

## Quick Start

### 1. Add Dependency

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.company</groupId>
    <artifactId>business-support-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. Configure Properties

Add the following configuration to your `application.yml`:

```yaml
business-support:
  enabled-modules:
    global-exception: true    # Global exception handling
    annotation-log: true      # Annotation-based logging
    trace-id: true           # Log trace tracking
    redis-lock: true         # Redis distributed lock
    multi-datasource: true   # Multi-datasource support
    mq-support: true         # MQ support
```

## Usage Examples

### Global Exception Handling

The component automatically catches exceptions and returns a unified error format:

```json
{
  "code": 500,
  "message": "Internal Server Error",
  "data": null
}
```

### Method Logging

Use the `@Loggable` annotation to log method calls:

```java
@Loggable(level = "INFO", printArgs = true, printResult = true)
public String getUserInfo(String userId) {
    return userService.getById(userId);
}
```

### Redis Distributed Lock

Use the `@RedisLock` annotation for distributed locking:

```java
@RedisLock(key = "order:lock:#{orderId}", expire = 30)
public void processOrder(String orderId) {
    // Business logic
}
```

### Multi-datasource Support

Use the `@DataSource` annotation to switch data sources:

```java
@DataSource("secondary")
public List<User> getUsers() {
    return jdbcTemplate.query("SELECT * FROM users");
}
```

### MQ Support

Use the provided MQ template for message operations:

```java
@Autowired
private MqTemplate mqTemplate;

// Send message
mqTemplate.send("order.queue", order);

// Listen to messages
@MqListener(queue = "order.queue")
public void handleOrder(Order order) {
    // Process message
}
```

## Requirements

- JDK 1.8+
- Spring Boot 2.5+
- Spring Cloud 2021.0.3+

## License

This project is licensed under the MIT License - see the LICENSE file for details. 