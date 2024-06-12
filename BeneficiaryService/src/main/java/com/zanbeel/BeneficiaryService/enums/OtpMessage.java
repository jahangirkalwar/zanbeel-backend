package com.zanbeel.BeneficiaryService.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum OtpMessage {
    OTP_EXPIRED(1, "OTP Expired"),
    INVALID_OTP(2, "Invalid OTP"),
    OTP_CODE_REDEEM(3, "OTP code redeem Successfully");

    private int code;
    private String value;
}
