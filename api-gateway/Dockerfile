FROM openjdk:17-jdk-alpine

ARG JAR_FILE=build/libs/*.jar

COPY ../build/libs/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar","/api-gateway.jar"]