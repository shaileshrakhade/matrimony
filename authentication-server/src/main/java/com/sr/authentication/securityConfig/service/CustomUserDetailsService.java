package com.sr.authentication.securityConfig.service;


import com.sr.authentication.dao.UserCredentialDto;
import com.sr.authentication.securityConfig.dto.CustomUserDetailsDto;
import com.sr.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    @Lazy
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentialDto> user = userService.getUserCredentialDtoByUserName(username);
        return user.map(CustomUserDetailsDto::new).orElseThrow(() -> new UsernameNotFoundException("Username or Password is incorrect..."));
    }
}
