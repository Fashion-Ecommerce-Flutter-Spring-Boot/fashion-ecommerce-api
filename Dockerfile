
# ==========================================
# Stage 1: Build Spring Boot Application
# ==========================================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven configuration
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests


# ==========================================
# Stage 2: Run Spring Boot Application
# ==========================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy generated JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]
