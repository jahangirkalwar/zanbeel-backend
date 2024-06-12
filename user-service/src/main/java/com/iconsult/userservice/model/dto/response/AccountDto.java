package com.iconsult.userservice.model.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AccountDto implements Serializable
{
    private Long id;
    private String mobileNumber;
    private String firstName;
    private String lastName;
    private String cnic;
    private String email;
    private String userName;
    private String password;
    private String securityPicture;
    private List<AccountDTO> accountList;

    // Nested class representing account details
    public static class AccountDTO {
        private String name;
        private String cnic;
        private String dob;
        private String accountNumber;
        private String accountType;
        private String branch;
        private String bankName;
        private String city;
        private String email;
        private String cellNumber;
        private String cnicIssuance;
        private String cnicExpiry;
        private String purposeOfAccount;
        private String sourceOfIncome;
        private String residentialAddress;
        private String lineOfBusiness;
        private String businessAddress;
        private Double balance;
        private Long customerId;

        // Getters and Setters
    }
}
