# Stage 1: Build the JAR using Maven + JDK 17
FROM maven:3.9.2-openjdk-17 AS build
WORKDIR /app

# Copy Maven config and source code
COPY pom.xml .
COPY src ./src

# Build the JAR (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Use a smaller JDK image to run the app
FROM openjdk:17-slim
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/journalApp-0.0.1-SNAPSHOT.jar journalapp.jar

# Expose the app port (can be overridden by SERVER_PORT env)
EXPOSE 8081

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "journalapp.jar"]