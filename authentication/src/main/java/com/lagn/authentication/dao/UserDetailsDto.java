package com.lagn.authentication.dao;

import com.lagn.authentication.enums.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {
    private String phoneNumber;
    private String password;
    private String email;
    private String name;
    private String whatsAppNo;
    private String birthdate;
    private String address;
    private String gender;
}
