# Development container for Spring Boot + Thymeleaf
FROM mcr.microsoft.com/devcontainers/java:17-bullseye

# Update package manager
RUN apt-get update && apt-get upgrade -y

# Install basic tools
RUN apt-get install -y \
    git \
    curl \
    wget \
    zip \
    unzip \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Verify Java installation
RUN java -version

# Verify Maven installation
RUN mvn -version

# Set environment variables
ENV JAVA_HOME=/usr/local/sdkman/candidates/java/current \
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
