FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY ../config-server/mvnw .
COPY ../config-server/.mvn .mvn
COPY ../config-server/pom.xml .

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

COPY ../config-server/src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app/app.jar"]