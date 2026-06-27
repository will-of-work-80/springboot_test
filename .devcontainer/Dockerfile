# Development container for Spring Boot + Thymeleaf
FROM mcr.microsoft.com/devcontainers/java:17-alpine

# Update package manager and install tools
RUN apk update && apk add --no-cache \
    git \
    curl \
    wget \
    zip \
    unzip \
    maven \
    openjdk17

# Verify Java installation
RUN java -version

# Verify Maven installation
RUN mvn -version

# Set environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk \
    MAVEN_HOME=/usr \
    PATH=$JAVA_HOME/bin:/usr/bin:$PATH

# Create vscode user
RUN useradd -m -s /bin/bash vscode || true

# Set working directory
WORKDIR /workspace

# Maven settings for faster builds
RUN mkdir -p /home/vscode/.m2 \
    && chown -R vscode:vscode /home/vscode/.m2

USER vscode
