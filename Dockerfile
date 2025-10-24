# Stage 1: Build the JAR using Maven + JDK
FROM maven:3.9.2-jdk-21 AS build
WORKDIR /app

# Copy Maven config and source code
COPY pom.xml .
COPY src ./src

# Build the JAR (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Use a smaller JDK image to run the app
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/journalApp-0.0.1-SNAPSHOT.jar journalapp.jar

# Expose the app port
EXPOSE 8081

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "journalapp.jar"]