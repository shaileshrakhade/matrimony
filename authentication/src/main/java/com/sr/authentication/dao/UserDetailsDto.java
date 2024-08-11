package com.sr.authentication.dao;

import com.sr.authentication.enums.Provider;
import com.sr.authentication.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {
    private Long id;
    private String userName;
    private String phoneNumber;
    private String emailId;
    private String fullName;
    private String whatsAppNo;
    private String birthDate;
    private String address;
    private String gender;
    private Date updateOn;
    private Date tokenGeneratorOn;
    private Provider provider;
    private String otp;
    private Date otpExpiryOn;
    private Role roles;
}
