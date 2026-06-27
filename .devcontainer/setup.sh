#!/bin/bash
set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}=========================================="
echo "Spring Boot + Thymeleaf Setup Script"
echo "==========================================${NC}"

# Function to check command existence
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Java バージョン確認
echo ""
echo -e "${YELLOW}📦 Checking Java...${NC}"
if command_exists java; then
    java -version 2>&1
    echo -e "${GREEN}✅ Java found${NC}"
else
    echo -e "${RED}❌ Java not found!${NC}"
    exit 1
fi

# Maven のインストール確認
echo ""
echo -e "${YELLOW}📦 Checking Maven...${NC}"
if command_exists mvn; then
    mvn -version
    echo -e "${GREEN}✅ Maven found${NC}"
else
    echo -e "${RED}⚠️  Maven not found!${NC}"
    echo -e "${YELLOW}Attempting to install Maven...${NC}"

    # Try Alpine Linux package manager (apk)
    if command_exists apk; then
        apk update || true
        apk add --no-cache maven openjdk17 || {
            echo -e "${RED}Failed to install Maven via apk${NC}"
            exit 1
        }
    # Try Debian/Ubuntu package manager (apt-get)
    elif command_exists apt-get; then
        apt-get update || true
        apt-get install -y maven || {
            echo -e "${RED}Failed to install Maven via apt-get${NC}"
            exit 1
        }
    else
        echo -e "${RED}❌ No compatible package manager found${NC}"
        exit 1
    fi

    # Verify installation
    if command_exists mvn; then
        mvn -version
        echo -e "${GREEN}✅ Maven installed successfully${NC}"
    else
        echo -e "${RED}❌ Maven installation failed${NC}"
        exit 1
    fi
fi

# Maven キャッシュの作成
echo ""
echo -e "${YELLOW}📦 Setting up Maven cache...${NC}"
mkdir -p ~/.m2 || true
mkdir -p /root/.m2 || true
echo -e "${GREEN}✅ Maven cache directories created${NC}"

# プロジェクトビルド
echo ""
echo -e "${GREEN}=========================================="
echo "🔨 Building project..."
echo "==========================================${NC}"
echo ""

if [ -f "pom.xml" ]; then
    mvn clean install || {
        echo -e "${RED}Build failed!${NC}"
        exit 1
    }
    echo -e "${GREEN}✅ Build successful${NC}"
else
    echo -e "${RED}❌ pom.xml not found${NC}"
    exit 1
fi

echo ""
echo -e "${GREEN}=========================================="
echo "✅ Setup completed successfully!"
echo "==========================================${NC}"
echo ""
echo -e "${YELLOW}You can now run:${NC}"
echo "  mvn spring-boot:run"
echo ""
echo -e "${YELLOW}Or access the application at:${NC}"
echo "  http://localhost:8080/"
echo ""
