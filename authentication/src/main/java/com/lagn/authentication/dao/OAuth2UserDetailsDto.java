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
public class OAuth2UserDetailsDto {
    private String mobile;
    private String password;
    private String email;
    private String name;
    private String whatsAppNo;
    private Provider provider;
}
