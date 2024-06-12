package com.zanbeel.BeneficiaryService.service;


import com.zanbeel.BeneficiaryService.model.dto.request.BeneficiaryRequestDto;
import com.zanbeel.BeneficiaryService.model.dto.request.OtpRequestDto;
import com.zanbeel.BeneficiaryService.model.dto.request.UpdateBeneficiaryRequestDto;
import com.zanbeel.BeneficiaryService.model.dto.response.BeneficiaryResponseDto;
import com.zanbeel.customUtility.model.CustomResponseEntity;
import com.zanbeel.BeneficiaryService.model.entity.Beneficiary;

import java.util.List;


public interface BeneficiaryService {
    CustomResponseEntity<BeneficiaryResponseDto> getBeneficiaryById(Long beneficiaryId);

    CustomResponseEntity<List<BeneficiaryResponseDto>> getAllBeneficiaryByCustomerId(Long customerId);

    CustomResponseEntity<String> deleteBeneficiaryById(Long BeneficiaryId);

    CustomResponseEntity<OtpRequestDto> addBeneficiary(BeneficiaryRequestDto beneficiaryRequestDto);

    CustomResponseEntity<Beneficiary> updateBeneficiary(Long beneficiaryId, UpdateBeneficiaryRequestDto updateBeneficiaryRequestDto);
}
