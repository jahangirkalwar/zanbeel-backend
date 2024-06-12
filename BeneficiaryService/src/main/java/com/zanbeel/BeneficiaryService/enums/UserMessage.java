package com.zanbeel.BeneficiaryService.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum UserMessage {
    USER_NOT_FOUND(1, "Customer not Found");

    private int code;
    private String value;
}
