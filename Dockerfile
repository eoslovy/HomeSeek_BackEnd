FROM openjdk:18-jdk-slim

# 필수 패키지 설치
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
    xvfb \
    libxi6 \
    libgconf-2-4 \
    libnss3 \
    libatk1.0-0 \
    libatk-bridge2.0-0 \
    libcups2 \
    libdrm2 \
    libxcomposite1 \
    libxdamage1 \
    libxfixes3 \
    libxrandr2 \
    libgbm1 \
    libasound2 \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-chrome.gpg \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# ChromeDriver 설치 (최신 안정 버전 사용)
RUN wget -q "https://chromedriver.storage.googleapis.com/LATEST_RELEASE" -O /tmp/chrome_version \
    && echo "Using ChromeDriver Version: $(cat /tmp/chrome_version)" \
    && wget -q "https://chromedriver.storage.googleapis.com/$(cat /tmp/chrome_version)/chromedriver_linux64.zip" -O /tmp/chromedriver.zip \
    && unzip /tmp/chromedriver.zip -d /usr/local/bin \
    && rm /tmp/chromedriver.zip /tmp/chrome_version \
    && chmod +x /usr/local/bin/chromedriver

WORKDIR /app

# 애플리케이션 설정 복사
COPY src/main/resources/application.properties /app/config/

# JAR 파일 복사
COPY build/libs/*.jar app.jar

# 컨테이너에서 애플리케이션이 사용하는 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-Dspring.config.location=/app/config/application.properties", "-jar", "app.jar"]
