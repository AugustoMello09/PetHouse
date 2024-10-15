FROM amazoncorretto:17-alpine-jdk AS jdk-builder

WORKDIR /app

COPY ./eureka ./

RUN chmod +x mvnw

RUN ./mvnw package 

FROM amazoncorretto:17-alpine

WORKDIR /app

COPY --from=jdk-builder /app/target/eureka-0.0.1-SNAPSHOT.jar .

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "eureka-0.0.1-SNAPSHOT.jar"]