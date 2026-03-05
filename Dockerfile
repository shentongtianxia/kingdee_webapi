# 使用OpenJDK 8作为基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制Maven配置文件
COPY pom.xml .
COPY lib/ ./lib/

# 复制源代码
COPY src/ ./src/

# 安装Maven（如果需要的话，或者使用多阶段构建）
RUN apk add --no-cache maven

# 构建应用
RUN mvn clean package -DskipTests

# 复制构建好的jar文件
COPY target/kingdee-webapi.jar app.jar

# 创建日志目录
RUN mkdir -p logs

# 暴露端口
EXPOSE 8080

# 设置JVM参数
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 