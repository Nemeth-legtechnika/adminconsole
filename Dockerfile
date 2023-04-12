FROM openjdk:17-jdk-alpine

ARG JAR_FILE=build/libs/*.jar

COPY ./build/libs/adminconsole-0.0.1-SNAPSHOT.jar adminconsole.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.datasource.url=jdbc:postgresql://172.20.0.3:5432/nemeth","/adminconsole.jar"]