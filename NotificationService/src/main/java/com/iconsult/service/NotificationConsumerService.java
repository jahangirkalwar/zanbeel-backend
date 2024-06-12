package com.iconsult.service;

import com.iconsult.userservice.model.dto.response.KafkaMessageDto;
import org.springframework.messaging.handler.annotation.Payload;

public interface NotificationConsumerService {

    public void consumeMsg(@Payload KafkaMessageDto otpDto);
}
