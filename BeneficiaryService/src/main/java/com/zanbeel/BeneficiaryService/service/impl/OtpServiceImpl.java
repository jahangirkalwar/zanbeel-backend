package com.zanbeel.BeneficiaryService.service.impl;

import com.iconsult.userservice.model.dto.request.CreateOTPDto;
import com.zanbeel.BeneficiaryService.enums.BeneficiaryMessage;
import com.zanbeel.BeneficiaryService.enums.OtpMessage;
//import com.zanbeel.BeneficiaryService.model.dto.request.CreateOtpDto;
//import com.zanbeel.BeneficiaryService.model.dto.request.CreateOtpDto;
import com.zanbeel.BeneficiaryService.model.dto.request.OtpRequestDto;
import com.zanbeel.BeneficiaryService.model.dto.request.ValidatedOtpRequestDto;
import com.zanbeel.BeneficiaryService.model.entity.Beneficiary;
import com.zanbeel.BeneficiaryService.model.entity.Otp;
import com.zanbeel.BeneficiaryService.model.mapper.OtpMapper;
import com.zanbeel.BeneficiaryService.repository.BeneficiaryRepository;
import com.zanbeel.BeneficiaryService.repository.OtpRepository;
import com.zanbeel.BeneficiaryService.service.OtpService;
import com.zanbeel.BeneficiaryService.util.Util;
import com.zanbeel.customUtility.exception.ServiceException;
import com.zanbeel.customUtility.model.CustomResponseEntity;
//import com.zanbeel.notificationservice.model.dto.request.CreateOTPDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OtpServiceImpl.class);
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    BeneficiaryRepository beneficiaryRepository;

    @Override
    public Otp createAndSendOTP(OtpRequestDto customerResponseDto) {
        String otpCode = Util.generateOTP(5);
        Optional<Otp> optionalOtp = otpRepository.findByEmailAndMobileNumberAndBeneficiaryId(customerResponseDto.getEmail(),
                customerResponseDto.getMobileNumber(), customerResponseDto.getBeneficiary().getBeneficiaryId());
        Otp otp;
        LOGGER.info("Generating OTP...");
        if(optionalOtp.isPresent()) {
            otp = optionalOtp.get();
            otp.setOtpCode(otpCode);
            otp.setSmsMessage("Dear Customer, your OTP to complete your request is " + otpCode);
            otp.setExpiryDateTime(LocalDateTime.now().plusMinutes(15));
        } else {
            otp = new Otp();
            otp.setMobileNumber(customerResponseDto.getMobileNumber());
            otp.setEmail(customerResponseDto.getEmail());
            otp.setOtpCode(otpCode);
            otp.setCreateDateTime(LocalDateTime.now());
            otp.setExpiryDateTime(LocalDateTime.now().plusMinutes(15));
            otp.setBeneficiaryId(customerResponseDto.getBeneficiary().getBeneficiaryId());
            otp.setSmsMessage("Dear Customer, your OTP to complete your request is " + otpCode);
        }
        otpRepository.save(otp);
        LOGGER.info("OTP Generated...");
            CreateOTPDto createOTPDto = OtpMapper.INSTANCE.mapOtpToCreateOtpDto(otp);
            LOGGER.info("OTP has been saved with Id: {}", otp.getId());
            kafkaTemplate.send("OTP", createOTPDto);
            LOGGER.info("OTP Sent Successfully to [{}]", createOTPDto.getMobileNumber());
            return otp;
    }

    @Override
    public CustomResponseEntity<Otp> addOtp(OtpRequestDto otpRequestDto) {
        Optional<Otp> optionalOtp = otpRepository.findByEmailAndMobileNumberAndBeneficiaryId(otpRequestDto.getEmail(),
                otpRequestDto.getMobileNumber(), otpRequestDto.getBeneficiary().getBeneficiaryId());
        if (optionalOtp.isPresent()) {
            throw new ServiceException(BeneficiaryMessage.BENEFICIARY_NOT_FOUND.getValue());
        }
        Otp otp = createAndSendOTP(otpRequestDto);
        return CustomResponseEntity.<Otp>builder().data(otp).build();
    }

    @Override
    public CustomResponseEntity<String> verifyOtp(ValidatedOtpRequestDto otpRequestDto) {
        Optional<Otp> optionalOtp = otpRepository.findByEmailAndMobileNumberAndBeneficiaryIdAndOtpCodeOrderByIdDesc(
                otpRequestDto.getEmail(),
                otpRequestDto.getMobileNumber(), otpRequestDto.getBeneficiary().getBeneficiaryId(), otpRequestDto.getOtpCode()
        );
        if (optionalOtp.isPresent()) {
            if (optionalOtp.get().getExpiryDateTime().isBefore(LocalDateTime.now())) {
                throw new ServiceException(OtpMessage.OTP_EXPIRED.getValue());
            } else {
                Optional<Beneficiary> beneficiary = beneficiaryRepository.findById(otpRequestDto.getBeneficiary().getBeneficiaryId());
                if (beneficiary.isPresent()) {
                    beneficiary.get().setIsActive(true);
                    beneficiaryRepository.save(beneficiary.get());
                    List<Otp> otps = otpRepository.findByBeneficiaryId(otpRequestDto.getBeneficiary().getBeneficiaryId());
                    otpRepository.deleteAll(otps);
                }
            }
        } else {
            throw new ServiceException(OtpMessage.INVALID_OTP.getValue());
        }
        return CustomResponseEntity.<String>builder().data(OtpMessage.OTP_CODE_REDEEM.getValue()).build();
    }
}
