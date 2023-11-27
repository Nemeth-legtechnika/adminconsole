FROM openjdk:17-jdk-alpine

ARG JAR_FILE=build/libs/*.jar

COPY ../build/libs/products-0.0.1-SNAPSHOT.jar products.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","/products.jar"]