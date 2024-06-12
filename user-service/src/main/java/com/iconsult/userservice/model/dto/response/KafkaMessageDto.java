package com.iconsult.userservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessageDto
{
    private String recipient, subject, message;
    private Boolean emailFlag, smsFlag;
}
