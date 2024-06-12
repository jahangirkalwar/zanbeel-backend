package com.iconsult.userservice.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordDto
{
    private String password, token;
}
