# Dockerfile content
# FROM openjdk:17
# WORKDIR /app
# COPY target/service-registery.jar /service-registery.jar
# EXPOSE 8761
# CMD ["java", "-jar", "service-registery.jar"]

FROM openjdk:17-alpine
ADD target/service-registry.jar /service-registry.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/service-registry.jar"]
