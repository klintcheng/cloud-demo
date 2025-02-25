# 使用 Maven 镜像来构建应用
FROM maven:3.9.9-ibm-semeru-23-jammy AS build

# 设置工作目录
WORKDIR /app

COPY pom.xml /app
COPY common-api/pom.xml /app/common-api/pom.xml
COPY common-api/src /app/common-api/src
COPY gateway/pom.xml /app/gateway/pom.xml
COPY gateway/src /app/gateway/src

RUN ls
# 先安装父 POM 到本地仓库
RUN mvn clean install -N


# 使用 Maven 构建项目，生成 JAR 文件
RUN mvn clean package -pl gateway -am -DskipTests

# 使用轻量级的 OpenJDK 镜像来运行应用
FROM eclipse-temurin:21-alpine

# 设置工作目录
WORKDIR /app

# 从 build 阶段复制构建好的 JAR 文件到当前容器
COPY --from=build /app/gateway/target/*.jar app.jar

# 暴露应用端口
EXPOSE 8080

# 运行 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]