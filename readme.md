
# 1. 系统

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [1. 系统](#1-系统)
  - [1.1. 启动依赖服务](#11-启动依赖服务)
    - [1.1.1. start consul](#111-start-consul)
    - [1.1.2. start mysql](#112-start-mysql)
    - [1.1.3. start redis](#113-start-redis)
    - [1.1.4. start gateway](#114-start-gateway)
  - [1.2. 配置](#12-配置)
    - [1.2.1. 初始化数据](#121-初始化数据)
  - [1.3. run service](#13-run-service)

<!-- /code_chunk_output -->



## 1.1. 启动依赖服务

### 1.1.1. start consul

```sh
docker run -d -p 8500:8500 --name=dev-consul -e CONSUL_BIND_INTERFACE=eth0 consul:1.15.4

# docker run -d -e CONSUL_BIND_INTERFACE=eth0 consul:1.15.4 agent -dev -join=172.17.0.2
```

### 1.1.2. start mysql

```sh
docker run -d \
  --name mysql8 \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -e MYSQL_DATABASE=springdemo \
  -e MYSQL_PASSWORD=123456 \
  -p 3306:3306 \
  m.daocloud.io/docker.io/library/mysql:latest
```

### 1.1.3. start redis

```sh
docker run -d \
  --name redis \
  -p 6379:6379 \
  redis:latest
```

### 1.1.4. start gateway

首先生成本地镜像，也可以不用docker启动，直接代码启动网关服务。

```sh
docker run -p 8080:8080 -d -e SPRING_CLOUD_CONSUL_HOST=192.168.31.207 gateway:latest

```

## 1.2. 配置

springcloud 配置读取 consul 配置文件的优先级如下：

```sh
config/testApp,dev/
config/testApp/
config/application,dev/
config/application/
```
因此，通过application加环境变量可以配置全局配置。

### 1.2.1. 初始化数据

1. 执行 shell/consul.sh  会初始化配置
2. 执行 shell/init.sql  初始化表和数据

## 1.3. run service

```sh
> cd service-user
> mvn spring-boot:run
```