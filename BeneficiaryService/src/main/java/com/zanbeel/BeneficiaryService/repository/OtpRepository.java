package com.zanbeel.BeneficiaryService.repository;

import com.zanbeel.BeneficiaryService.model.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmailAndMobileNumberAndBeneficiaryId(String email,String mobileNumber, Long beneficiaryId);
    Optional<Otp> findByEmailAndMobileNumberAndBeneficiaryIdAndOtpCodeOrderByIdDesc(String email,String mobileNumber, Long beneficiaryId, String otpCode);
    List<Otp> findByBeneficiaryId(Long beneficiaryId);
}
