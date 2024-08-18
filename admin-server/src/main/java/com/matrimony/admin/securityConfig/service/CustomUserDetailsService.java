package com.matrimony.admin.securityConfig.service;


import com.matrimony.admin.securityConfig.dto.CustomUserDetailsDto;
import com.matrimony.admin.util.jwt.CustomKeysEnums;
import com.matrimony.admin.util.jwt.JwtClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Value("${spring.security.user.name}")
    private String username;
    @Value("${spring.security.user.password}")
    private String password;

    @Value("${spring.security.application.secret-key}")
    private String applicationSecretKey;

    @Autowired
    private JwtClaims jwtClaims;


    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        if (jwtClaims.valueFromToken(token, CustomKeysEnums.APPLICATION).equals(applicationSecretKey))
            return new CustomUserDetailsDto(username, password, jwtClaims.extractRoles(token), true);
        else
            throw new RuntimeException("Token Information Is Not Valid.");
    }

}
