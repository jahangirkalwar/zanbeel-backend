package com.zanbeel.BeneficiaryService.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryRequestDto {
    private Long beneficiaryId;
    private Long customerId;
    @NotBlank(message = "bankName cannot be blank")
    private String bankName;
    @NotBlank(message = "title cannot be blank")
    private String title;
    @NotBlank(message = "accountNumber cannot be blank")
    private String accountNumber;
    private String nickName;

}
