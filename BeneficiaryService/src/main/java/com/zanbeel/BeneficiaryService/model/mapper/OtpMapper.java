package com.zanbeel.BeneficiaryService.model.mapper;

import com.iconsult.userservice.model.dto.request.CreateOTPDto;
import com.zanbeel.BeneficiaryService.model.dto.request.BeneficiaryRequestDto;
//import com.zanbeel.BeneficiaryService.model.dto.request.CreateOtpDto;
//import com.zanbeel.BeneficiaryService.model.dto.request.CreateOtpDto;
import com.zanbeel.BeneficiaryService.model.entity.Beneficiary;
import com.zanbeel.BeneficiaryService.model.entity.Otp;
//import com.zanbeel.notificationservice.model.dto.request.CreateOTPDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OtpMapper {
    OtpMapper INSTANCE = Mappers.getMapper(OtpMapper.class);

//    @Mapping(target = "otp", source = "otp.otpCode")
//    CreateOtpDto mapOtpToCreateOtpDto(Otp otp);
    @Mapping(target = "otp", source = "otp.otpCode")
    CreateOTPDto mapOtpToCreateOtpDto(Otp otp);
}
