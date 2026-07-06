# Etapa 1: compila o projeto
FROM maven:3.9-eclipse-temurin-24 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: imagem final, enxuta
FROM eclipse-temurin:24-jre-alpine
LABEL maintainer="financiabank.com"
WORKDIR /myapp
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]