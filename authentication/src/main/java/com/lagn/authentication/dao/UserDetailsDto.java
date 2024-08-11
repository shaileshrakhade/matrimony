package com.lagn.authentication.dao;

import com.lagn.authentication.enums.Provider;
import com.lagn.authentication.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Collection;
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
