package com.iconsult.service.impl;

import com.iconsult.userservice.model.dto.response.KafkaMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
    private JavaMailSender javaMailSender;


    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Async
    public void sendEmail(SimpleMailMessage mailMessage, String email, KafkaMessageDto kafkaMessageDto) {
        mailMessage.setTo(kafkaMessageDto.getRecipient());
        mailMessage.setSubject(kafkaMessageDto.getSubject());
        mailMessage.setFrom(email);
        mailMessage.setText(kafkaMessageDto.getMessage());


        javaMailSender.send(mailMessage);
    }
}
