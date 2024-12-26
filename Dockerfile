FROM openjdk:18-jdk-slim

# Chrome 및 필요한 패키지 설치
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
    xvfb \
    libxi6 \
    libgconf-2-4 \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-chrome.gpg \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# ChromeDriver 설치
RUN CHROME_VERSION=$(google-chrome --version | awk '{print $3}' | awk -F'.' '{print $1}') \
    && wget -q "https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/${CHROME_VERSION}/linux64/chromedriver-linux64.zip" -O /tmp/chromedriver.zip \
    && unzip /tmp/chromedriver.zip -d /usr/local/bin \
    && mv /usr/local/bin/chromedriver-linux64/chromedriver /usr/local/bin/ \
    && rm -rf /usr/local/bin/chromedriver-linux64 \
    && rm /tmp/chromedriver.zip \
    && chmod +x /usr/local/bin/chromedriver

WORKDIR /app

# 애플리케이션 설정 파일 복사
COPY src/main/resources/application.properties /app/config/

# JAR 파일 복사
COPY build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.config.location=/app/config/application.properties", "-jar", "app.jar"]