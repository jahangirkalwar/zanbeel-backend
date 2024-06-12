package com.zanbeel.BeneficiaryService.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum BeneficiaryMessage {
    BENEFICIARY_DELETED(1, "Beneficiary deleted successfully"),
    BENEFICIARY_NOT_FOUND(2, "Beneficiary not Found"),
    BENEFICIARY_ALREADY_EXIST(3, "Beneficiary for this account already exist"),
    BENEFICIARY_FOUND(4, "Beneficiary Found"),
    BENEFICIARY_UPDATED(5, "Beneficiary updated successfully");

    private int code;
    private String value;
}
