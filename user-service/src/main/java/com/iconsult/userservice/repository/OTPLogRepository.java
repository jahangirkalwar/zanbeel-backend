package com.iconsult.userservice.repository;

import com.iconsult.userservice.model.entity.OTPLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OTPLogRepository extends JpaRepository<OTPLog, Long> {

    List<OTPLog> findByMobileNumberAndIsExpired(String mobileNumber, Boolean isExpired);
}
