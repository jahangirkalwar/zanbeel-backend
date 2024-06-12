FROM openjdk:17-alpine
ADD target/notification-service.jar /notification-service.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/notification-service.jar"]