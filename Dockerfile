# Use official OpenJDK 21 slim image
FROM openjdk:21-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the correct JAR from target
COPY target/journalApp-0.0.1-SNAPSHOT.jar journalapp.jar

# Expose the port from environment
EXPOSE 8081

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "journalapp.jar"]