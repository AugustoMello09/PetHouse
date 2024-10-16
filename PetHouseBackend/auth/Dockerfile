FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 0
ENTRYPOINT ["java","-jar","/app.jar"]