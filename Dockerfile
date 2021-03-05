FROM openjdk:11.0.6-jre-slim
ARG JAR_FILE=target/person-service-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} person-service.jar
ENTRYPOINT ["java","-jar","/person-service.jar"]