package com.zanbeel.BeneficiaryService.model.dto.request;

import com.zanbeel.BeneficiaryService.model.entity.Beneficiary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequestDto {
    private String mobileNumber;
    private String email;
    private BeneficiaryRequestDto beneficiary;

}
