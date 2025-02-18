
# 系统

## 配置

springcloud 配置读取 consul 配置文件的优先级如下：

```
config/testApp,dev/
config/testApp/
config/application,dev/
config/application/
```
因此，通过application加环境变量可以配置全局配置。

### 添加环境全局配置。

以开发环境为例，consul 中配置如下 key:
> **config/application.dev/data**

比如把 db ,redis 之类的配置完成。

```
spring:
  datasource:
    password: 123456
    url: jdbc:mysql://localhost:3306/springdemo?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
    username: root
```

### 添加服务的配置

服务默认的配置 key，以 dev环境为例:

> config/service-user.dev/data