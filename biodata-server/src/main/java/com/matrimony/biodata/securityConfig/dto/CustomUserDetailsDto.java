package com.matrimony.biodata.securityConfig.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetailsDto implements UserDetails {
    private final String username;
    private final String password;
    private final List<String> role;
    private final boolean isActive;

    public CustomUserDetailsDto(String username, String password, List<String> role, boolean isActive) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        role.forEach(s -> {
            list.add(new SimpleGrantedAuthority(s));
        });

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
