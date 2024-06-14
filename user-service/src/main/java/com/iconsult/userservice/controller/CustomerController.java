package com.iconsult.userservice.controller;

import com.iconsult.userservice.model.dto.request.*;
import com.iconsult.userservice.model.entity.Customer;
import com.iconsult.userservice.service.AuthService;
import com.iconsult.userservice.service.Impl.CustomerServiceImpl;
import com.iconsult.userservice.service.Impl.OTPLogImpl;
import com.zanbeel.customUtility.model.CustomResponseEntity;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private OTPLogImpl otpLogImpl;

    @Autowired
    private AuthService authService;

    @GetMapping("/ping")
    @Hidden
    public String ping()
    {
        LOGGER.info("User-Service is running {}", LocalDateTime.now());
        return "Hello World";
    }

    @PostMapping("/signup")
    public CustomResponseEntity register(@Valid @RequestBody CustomerDto customerDto)
    {
        return this.customerServiceImpl.register(customerDto, otpLogImpl);
    }

    @PostMapping("/register")
    public CustomResponseEntity signUp(@Valid @RequestBody CustomerDto customerDto)
    {
        return this.customerServiceImpl.signup(customerDto, otpLogImpl);
    }

    @PostMapping("/createOTP")
    public CustomResponseEntity createOTP(@Valid @RequestBody OTPDto OTPDto)
    {
        return this.otpLogImpl.createOTP(OTPDto);
    }

    @PostMapping("/verifyOTP")
    public CustomResponseEntity verifyOTP(@Valid @RequestBody OTPDto OTPDto)
    {
        return this.otpLogImpl.verifyOTP(OTPDto);
    }

    @GetMapping("/verifyCNIC")
    @Hidden
    public CustomResponseEntity verifyCNIC(@RequestParam String cnic , @RequestParam String email , @RequestParam String accountNumber)
    {
        return this.customerServiceImpl.verifyCNIC(cnic , email , accountNumber );
    }

    @PostMapping("/login")
    public CustomResponseEntity login(@Valid @RequestBody LoginDto loginDto)
    {

        return this.customerServiceImpl.login(loginDto);
    }

    @GetMapping("/validateToken")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }

    @PostMapping("/forgetUserName")
    public CustomResponseEntity forgotUserName(@Valid @RequestBody ForgetUsernameDto forgetUsernameDto)
    {
        return this.customerServiceImpl.forgetUserName(forgetUsernameDto);
    }

    @PostMapping("/forgetPassword")
    @Hidden
    public CustomResponseEntity forgetPassword(@Valid @RequestBody ForgetUsernameDto forgetUsernameDto)
    {
        return this.customerServiceImpl.forgetPassword(forgetUsernameDto);
    }

    @GetMapping("/verifyForgetPasswordToken")
    @Hidden
    public CustomResponseEntity verifyResetPasswordToken(@RequestParam String token)
    {
        return this.customerServiceImpl.verifyResetPasswordToken(token);
    }

    @PostMapping("/confirmForgetPassword")
    @Hidden
    public CustomResponseEntity resetPassword(@RequestBody ResetPasswordDto resetPasswordDto)
    {
        return this.customerServiceImpl.resetPassword(resetPasswordDto);
    }

    @GetMapping("/getCustomer/{id}")
    public CustomResponseEntity<Customer> findById(@PathVariable Long id)
    {
        return this.customerServiceImpl.findById(id);
    }
}