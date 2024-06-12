package com.iconsult.userservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum CustomerStatus
{
    ACTIVE("00"),
    TEMP_BLOCK("01"),
    PERMANENT_BLOCK("02"),
    FAILED_LOGIN_ATTEMPTS_BLOCK("05"),
    DISABLED_FOR_UPDATE_MOBILE("10");

    private String code;
}
