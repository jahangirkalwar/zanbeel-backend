package com.iconsult.userservice.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto
{
    @NotEmpty(message = "MobileNumber is mandatory")
    @Pattern(regexp = "^03\\d{9}$", message = "Invalid Mobile number")
    private String mobileNumber;

    //@NotEmpty(message = "First Name is mandatory")
    private String firstName;

    //@NotEmpty(message = "Last Name is mandatory")
    private String lastName;

    @NotEmpty(message = "CNIC is mandatory")
    @Size(min = 13, message = "Invalid CNIC")
    private String cnic;

    //@NotEmpty(message = "Account Number is mandatory")
    //@Size(min = 16, message = "Invalid Account Number")
    private String accountNumber;

    @NotEmpty(message = "Email is mandatory")
    @Email
    private String email;

    @NotEmpty(message = "User Name is mandatory")
    private String userName;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotEmpty(message = "Security Picture is mandatory")
    private String securityPicture;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityPicture() {
        return securityPicture;
    }

    public void setSecurityPicture(String securityPicture) {
        this.securityPicture = securityPicture;
    }
}
