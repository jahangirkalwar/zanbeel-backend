package com.iconsult.userservice.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OTPDto {

    @NotEmpty(message = "Mobile Number is mandatory")
    @Pattern(regexp = "^03\\d{9}$", message = "Invalid Mobile number")
    private String mobileNumber;

    @Email
    private String email;

    private String otp;
    private String reason;

    public OTPDto(String mobileNumber, String email, String reason) {
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.reason = reason;
    }

    public OTPDto(String mobileNumber, String email, String reason, String otp) {
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.reason = reason;
        this.otp = otp;
    }
}
