package com.sr.authentication.dao;

import com.sr.authentication.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCredentialDto {
    private String userName;
    private String password;
    private String otp;
    private Role role;
    private  boolean isActive;

}
