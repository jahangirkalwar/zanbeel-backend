package com.zanbeel.BeneficiaryService.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    private Long id;

    private String mobileNumber;
    private String firstName;
    private String lastName;
    private String cnic;
    private String email;
    private String userName;
    private String password;
    private String securityPicture;

}
