# Stage 1 - build
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

  # Copia pom.xml e baixa dependências
COPY pom.xml .
RUN mvn -B dependency:go-offline

  # Copia todo o código fonte
COPY src ./src

  # Compila sem rodar testes
RUN mvn clean package -DskipTests

  # Stage 2 - runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app

  # Copia JAR do stage de build
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]