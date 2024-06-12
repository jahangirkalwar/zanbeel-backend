package com.zanbeel.BeneficiaryService.model.dto.response;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryResponseDto {
    private Long beneficiaryId;
    private Long customerId;
    private String bankName;
    private String title;
    private String accountNumber;
    private String nickName;
}
