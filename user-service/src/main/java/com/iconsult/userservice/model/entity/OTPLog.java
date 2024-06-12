package com.iconsult.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "OTPLog")
@Getter
@Setter
public class OTPLog
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobileNumber;
    private String email;
    private String OTP;
    private Boolean isVerified;
    private Boolean isExpired;
    private Long createDateTime;
    private Long expiryDateTime;
    private Long verifyDateTime;
    private String smsMessage;
    private String txnRefNum;
    private String reason;
}
