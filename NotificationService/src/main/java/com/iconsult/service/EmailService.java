package com.iconsult.service;

import com.iconsult.userservice.model.dto.request.CreateOTPDto;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    public void sendEmail(SimpleMailMessage mailMessage, String email, CreateOTPDto otpDto);
}
