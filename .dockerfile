FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Add container support
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the JAR file
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# NOTE: We're setting the profile via environment variable, not in the properties file
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]