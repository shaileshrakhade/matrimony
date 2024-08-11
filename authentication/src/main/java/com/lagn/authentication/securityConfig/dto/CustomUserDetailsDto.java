package com.lagn.authentication.securityConfig.dto;

import com.lagn.authentication.dao.UserCredentialDto;
import com.lagn.authentication.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CustomUserDetailsDto implements UserDetails {
    private final String username;
    private final String password;
    private final Role role;
    private final boolean isActive;


    public CustomUserDetailsDto(UserCredentialDto userCredentialDto) {
        this.username = userCredentialDto.getUserName();
        this.password = userCredentialDto.getPassword();
        this.role = userCredentialDto.getRole();
        this.isActive = userCredentialDto.isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        if (this.role == Role.ADMIN) {
            list.add(new SimpleGrantedAuthority("ADMIN"));
        } else if (this.role == Role.USER) {
            list.add(new SimpleGrantedAuthority("USER"));
        }
        return list;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
