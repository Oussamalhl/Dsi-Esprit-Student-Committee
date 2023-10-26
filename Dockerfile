FROM openjdk:18-jdk-alpine3.14
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=target/dsi.esprit.authentication-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]