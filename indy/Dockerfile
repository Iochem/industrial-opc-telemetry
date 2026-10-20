# Stage 1 - build
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn -B dependency:go-offline

# copy all source code
COPY src ./src

# Compile without running tests
RUN mvn clean package -DskipTests

# Stage 2 - runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy JAR from the build stage
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]