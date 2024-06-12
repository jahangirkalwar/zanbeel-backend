package com.iconsult.userservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.topic.name}")
    private String otpTopic;
    @Bean
    public NewTopic topicOTP()
    {
        return TopicBuilder.name(otpTopic).build();
    }

    @Bean
    public NewTopic topicForgetUserName()
    {
        return TopicBuilder.name("forgetUserName").build();
    }
}
