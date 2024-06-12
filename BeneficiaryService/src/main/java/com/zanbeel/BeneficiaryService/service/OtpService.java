package com.zanbeel.BeneficiaryService.service;

import com.zanbeel.BeneficiaryService.model.dto.request.OtpRequestDto;
import com.zanbeel.BeneficiaryService.model.dto.request.ValidatedOtpRequestDto;
import com.zanbeel.BeneficiaryService.model.entity.Beneficiary;
import com.zanbeel.BeneficiaryService.model.entity.Otp;
import com.zanbeel.customUtility.model.CustomResponseEntity;

public interface OtpService {
    Otp createAndSendOTP(OtpRequestDto otpRequestDto);

    CustomResponseEntity<Otp> addOtp(OtpRequestDto otpRequestDto);

    CustomResponseEntity<String> verifyOtp(ValidatedOtpRequestDto otpRequestDto);
}

