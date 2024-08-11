package com.lagn.authentication.securityConfig.service;


import com.lagn.authentication.dao.UserCredentialDto;
import com.lagn.authentication.securityConfig.dto.CustomUserDetailsDto;
import com.lagn.authentication.service.UserService;
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
