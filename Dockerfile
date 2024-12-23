FROM openjdk:18-jdk-slim

WORKDIR /app

# 애플리케이션 설정 파일 복사
COPY src/main/resources/application.properties /app/config/

# JAR 파일 복사
COPY build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.config.location=/app/config/application.properties", "-jar", "app.jar"]
