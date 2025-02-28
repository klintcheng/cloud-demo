consul kv put config/application.dev/data "
---
spring:
  datasource:
    driver-class-name: com.p6spy.engine.spy.P6SpyDriver
    password: 123456
    url: jdbc:p6spy:mysql://localhost:3306/springdemo?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
    username: root
  redis:
    host: localhost
    port: 6379
    timeout: 2000
rocketmq:
  name-server: 192.168.31.207:9876
  producer:
    group: my-producer-group
  consumer:
    group: my-consumer-group"

consul kv put config/service-user.dev/data "
---
custom:
  message: hello local"