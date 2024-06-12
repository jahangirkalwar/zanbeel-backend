package com.iconsult.userservice.repository;

import com.iconsult.userservice.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    Customer findByEmail(String email);
    Customer findByMobileNumber(String mobileNumber);
    Customer findByUserName(String userName);
    Customer findByResetToken(String resetToken);

    @Query("SELECT c FROM Customer c WHERE c.email = :identifier OR c.userName = :identifier")
    Customer findByEmailOrUserName(String identifier);
}