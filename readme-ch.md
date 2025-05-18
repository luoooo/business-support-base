# business-support-base 技术分享文档

## 项目简介

`business-support-base` 是一个为企业级 Java 应用提供通用能力的基础组件库，基于 Spring Boot 生态，聚焦于分布式系统常用的中间件集成、通用异常处理、分布式锁、日志链路追踪等能力，提升业务开发效率和代码一致性。

## 主要功能模块

### 1. 全局异常处理
- 提供统一的全局异常捕获与响应格式。
- 支持业务异常、参数校验异常、系统异常等多种类型。
- 可通过配置灵活启用/禁用。
- 支持自定义异常码、错误信息前缀、是否打印堆栈等参数。

### 2. 分布式锁（RedisLock）
- 基于 Redis 实现分布式锁，支持注解式声明（@RedisLock）。
- 支持 SpEL 表达式灵活指定锁 key，自动绑定方法参数。
- 支持锁超时、重试、等待超时等参数配置。
- 适用于接口幂等、定时任务、分布式资源竞争等场景。

### 3. 日志链路追踪
- 自动生成 traceId 并在日志、请求上下文中传递。
- 支持与主流日志框架集成，便于分布式系统中问题定位和链路追踪。
- 可扩展支持自定义 traceId 生成策略。

### 4. Redis 工具集成
- 提供 Redis 分布式锁、缓存等常用能力的封装。
- 统一 RedisTemplate 配置，简化业务开发。
- 支持多 Redis 实例扩展。

### 5. MQ 支持
- 集成 RabbitMQ，提供消息发送、消费的通用封装。
- 支持消息幂等、重试、死信队列等机制。
- 可扩展支持其他 MQ（如 Kafka、RocketMQ）。

## 技术细节
- 基于 Spring Boot Starter 机制，自动装配，按需加载。
- 通过 @ConditionalOnProperty 精细控制各模块启用。
- 注解能力（如 @RedisLock）基于 Spring AOP + SpEL 实现，参数解析灵活。
- 全局异常处理基于 @RestControllerAdvice，支持多种异常类型统一响应。
- 日志链路追踪通过 Filter 拦截请求，自动注入 traceId。
- 代码高度解耦，便于二次开发和定制。

## 优缺点分析
### 优点
- 开箱即用，极大提升业务开发效率。
- 配置灵活，适配多种业务场景。
- 注解驱动，业务代码无侵入。
- 易于扩展和维护。

### 缺点
- 依赖 Spring Boot 生态，非 Spring 项目无法直接使用。
- 分布式锁等能力依赖 Redis，需保证 Redis 高可用。
- 目前仅内置 RabbitMQ，其他 MQ 需自行扩展。
- 统一异常处理可能与部分业务自定义异常处理冲突，需注意配置。

## 快速集成
1. 在业务项目的 pom.xml 中引入依赖：
   ```xml
   <dependency>
       <groupId>com.company</groupId>
       <artifactId>business-support-base</artifactId>
       <version>1.0.0</version>
   </dependency>
   ```
2. 在 application.yml/properties 中按需配置功能开关：
   ```yaml
   business-support:
     enabled-modules:
       global-exception: true
       redis-lock: true
       trace-id: true
       mq: true
   ```
3. 直接在业务代码中使用注解能力。

## 典型用法示例

### 1. 分布式锁
```java
@RedisLock(key = "#orderId", expire = 10, timeout = 5)
public void processOrder(String orderId) {
    // 业务逻辑
}
```

### 2. 全局异常处理
```java
// 只需开启配置，无需额外代码，所有异常自动被统一处理
```

### 3. 日志链路追踪
```java
// 日志中自动带有 traceId，无需手动处理
log.info("处理订单: {}", orderId); // 日志自动带 traceId
```

### 4. MQ 消息发送
```java
@Autowired
private RabbitTemplate rabbitTemplate;

public void sendMsg(String msg) {
    rabbitTemplate.convertAndSend("queueName", msg);
}
```

### 5. Redis 工具
```java
@Autowired
private StringRedisTemplate redisTemplate;

public void cacheValue(String key, String value) {
    redisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
}
```

## 配置项说明
- `business-support.enabled-modules.global-exception`：是否启用全局异常处理
- `business-support.enabled-modules.redis-lock`：是否启用分布式锁
- `business-support.enabled-modules.trace-id`：是否启用日志链路追踪
- `business-support.enabled-modules.mq`：是否启用 MQ 支持
- 其他详细参数请参考各模块源码注释

## 适用场景
- 需要统一异常处理、日志追踪、分布式锁等通用能力的企业级 Java 项目
- 微服务架构下的基础能力复用
- 需要快速集成中间件能力的业务系统

## 后期扩展建议
- 支持更多类型的分布式锁（如基于 Zookeeper、数据库等）。
- 增加对 Kafka、RocketMQ 等主流 MQ 的自动集成。
- 日志链路追踪支持与 SkyWalking、Zipkin 等链路追踪系统对接。
- 提供更多业务通用中间件能力，如限流、熔断、灰度发布等。
- 支持多租户、动态数据源等企业级特性。
- 提供可视化运维监控面板。

## 贡献与扩展
欢迎提交 issue 和 PR，参与共建！

---
如需详细技术细节或二次开发指导，请参考源码及注释。
