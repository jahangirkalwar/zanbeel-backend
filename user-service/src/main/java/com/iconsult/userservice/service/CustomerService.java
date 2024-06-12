package com.iconsult.userservice.service;



import com.iconsult.userservice.model.dto.request.*;
import com.iconsult.userservice.model.dto.response.ResponseDTO;
import com.iconsult.userservice.model.entity.Customer;
import com.iconsult.userservice.service.Impl.OTPLogImpl;
import com.zanbeel.customUtility.model.CustomResponseEntity;

public interface CustomerService
{
     Customer addUser(Customer customer);

    CustomResponseEntity register(CustomerDto customerDto, OTPLogImpl otpLogImpl);

    CustomResponseEntity login(LoginDto loginDto);

    void deleteUser(Long id);

    Customer updateCustomer(Customer customer);

    Customer findByEmailAddress(String email);

    Customer findByMobileNumber(String mobileNumber);

    Customer findByUserName(String userName);

    Customer findByResetToken(String resetToken);

    Customer save(Customer customer);

    CustomResponseEntity<Customer> findById(Long id);

    CustomResponseEntity forgetUserName(ForgetUsernameDto forgetUsernameDto);

    CustomResponseEntity verifyCNIC(String cnic, String email , String accountNumber);

    CustomResponseEntity forgetPassword(ForgetUsernameDto forgetUsernameDto);

    CustomResponseEntity verifyResetPasswordToken(String token);

    CustomResponseEntity resetPassword(ResetPasswordDto resetPasswordDto);
}