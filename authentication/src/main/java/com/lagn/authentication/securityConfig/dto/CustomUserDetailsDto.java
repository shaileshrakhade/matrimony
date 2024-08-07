package com.lagn.authentication.securityConfig.dto;

import com.lagn.authentication.dao.UserCredentialDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetailsDto implements UserDetails {
    private final String username;
    private final String password;

    public CustomUserDetailsDto(UserCredentialDto userCredentialDto) {
        this.username = userCredentialDto.getUserName();
        this.password = userCredentialDto.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}
