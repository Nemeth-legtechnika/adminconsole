FROM openjdk:17-jdk-alpine

ARG JAR_FILE=build/libs/*.jar

COPY ../build/libs/orders-0.0.1-SNAPSHOT.jar orders.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar","/orders.jar"]