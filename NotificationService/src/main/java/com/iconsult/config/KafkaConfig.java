package com.iconsult.config;


import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.config.TopicBuilder;



@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topicOTP()
    {
        return TopicBuilder.name("OTP").build();
    }

    @Bean
    public NewTopic topicForgetUserName()
    {
        return TopicBuilder.name("forgetUserName").build();
    }
//    @Bean
//    public Map<String, Object> consumerConfig() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "localhost:9092");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                JsonDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//
//        return props;
//    }
//
//    @Bean
//    public ConsumerFactory<String,UserDto> consumerFactory(){
//        return new DefaultKafkaConsumerFactory<>(consumerConfig());
//    }
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UserDto>> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, UserDto> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
}
