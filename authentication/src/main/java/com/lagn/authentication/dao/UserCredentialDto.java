package com.lagn.authentication.dao;

import com.lagn.authentication.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

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
