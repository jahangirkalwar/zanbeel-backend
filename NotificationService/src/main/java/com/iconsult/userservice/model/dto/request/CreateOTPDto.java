package com.iconsult.userservice.model.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreateOTPDto {

    private String mobileNumber;

    private String email;

    private String otp;
}
