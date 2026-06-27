# Development container for Spring Boot + Thymeleaf
FROM mcr.microsoft.com/devcontainers/java:17-bullseye

# Install additional tools
RUN apt-get update && apt-get install -y \
    git \
    curl \
    wget \
    zip \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Install Maven
ARG MAVEN_VERSION=3.9.0
RUN curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar xzf - -C /usr/local \
    && mv /usr/local/apache-maven-${MAVEN_VERSION} /usr/local/maven \
    && ln -s /usr/local/maven/bin/mvn /usr/local/bin/mvn

# Set environment variables
ENV JAVA_HOME=/usr/local/sdkman/candidates/java/current \
    MAVEN_HOME=/usr/local/maven \
    PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH

# Create vscode user
RUN useradd -m -s /bin/bash vscode || true

# Set working directory
WORKDIR /workspace

# Maven settings for faster builds
RUN mkdir -p /home/vscode/.m2 \
    && chown -R vscode:vscode /home/vscode/.m2

USER vscode
