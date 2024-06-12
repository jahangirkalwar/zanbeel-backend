package com.zanbeel.BeneficiaryService.repository;

import com.zanbeel.BeneficiaryService.model.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    List<Beneficiary> findByCustomerIdAndIsActive(Long userId, Boolean isActive);

    Optional<Beneficiary> findByAccountNumberAndCustomerId(String accountNumber, Long customerId);
}
