package com.zanbeel.BeneficiaryService.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBeneficiaryRequestDto {
    @NotBlank(message = "nickName cannot be null or empty")
    private String nickName;
}
