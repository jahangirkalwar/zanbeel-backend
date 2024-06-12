package com.iconsult.userservice.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    @NotEmpty(message = "Email/Username is mandatory")
    private String emailorUsername;
    @NotEmpty(message = "Password is mandatory")
    private String password;

    private String securityImage;
}
