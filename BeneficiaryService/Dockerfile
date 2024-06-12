# FROM openjdk:17
# ADD target/beneficiary-service.jar /beneficiary-service.jar
# EXPOSE 8083
# ENTRYPOINT ["java","-jar","/beneficiary-service.jar"]
# FROM api-gateway.jar


# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container at /app
COPY target/beneficiary-service.jar /app/

# Expose the port that your application will run on
EXPOSE 8083

# Environment variables for your application configuration
ENV SERVER_PORT=8083 \
    SPRING_KAFKA_PRODUCER_BOOTSTRAP_SERVERS=192.168.0.164:9092 \
    SPRING_KAFKA_PRODUCER_KEY_SERIALIZER=org.apache.kafka.common.serialization.StringSerializer \
    SPRING_KAFKA_PRODUCER_VALUE_SERIALIZER=org.springframework.kafka.support.serializer.JsonSerializer \
    SPRING_APPLICATION_NAME=BENEFICIARY-SERVICE \
    SPRING_DATASOURCE_URL=jdbc:mysql://192.168.0.3:3306/beneficiary \
    SPRING_DATASOURCE_USERNAME=user1 \
    SPRING_DATASOURCE_PASSWORD=User1#123 \
    SPRING_JPA_HIBERNATE_DIALECT=org.hibernate.dialect.MySQLDialect \
    SPRING_JPA_HIBERNATE_DDL_AUTO=update \
    SPRING_JPA_SHOW_SQL=true \
    EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka \
    EUREKA_CLIENT_FETCH_REGISTRY=true \
    EUREKA_CLIENT_REGISTER_WITH_EUREKA=true \
    EUREKA_INSTANCE_PREFER_IP_ADDRESS=true

# Run the JAR file when the container launches
CMD java -jar /app/beneficiary-service.jar
