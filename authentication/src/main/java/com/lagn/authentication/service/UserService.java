package com.lagn.authentication.service;

import com.lagn.authentication.securityConfig.service.CustomUserDetailsService;
import com.lagn.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import com.lagn.authentication.dao.UserCredentialDto;
import com.lagn.authentication.dao.UserDetailsDto;
import com.lagn.authentication.model.Users;
import com.lagn.authentication.repo.UserRepository;
import com.lagn.authentication.util.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public Optional<UserCredentialDto> getUserByEmail(String mobile) {
        return userRepository.findByEmail(mobile).map(user -> {
            return UserCredentialDto.builder().username(user.getEmail()).password(user.getPassword()).build();
        });
    }


    public Users createUser(UserDetailsDto userDetailsDto) throws SQLException {
        if (userDetailsDto.getPhoneNumber().length() != 10) throw new SQLException("Mobile Number is not valid");
        if (userDetailsDto.getEmail().isEmpty() || userDetailsDto.getEmail().isBlank())
            throw new SQLException("Email is required");
        if (userDetailsDto.getPassword().isEmpty() || userDetailsDto.getPassword().isBlank())
            throw new SQLException("Password is required");

        Users user = userRepository.findByEmail(userDetailsDto.getEmail())
                .stream().findFirst().orElseGet(() ->
                {
                    return Users.builder()
                            .email(userDetailsDto.getEmail())
                            .name(userDetailsDto.getName())
                            .password(passwordEncoder.encode(userDetailsDto.getPassword()))
                            .whatsAppNo(userDetailsDto.getWhatsAppNo())
                            .birthdate(userDetailsDto.getBirthdate())
                            .address(userDetailsDto.getAddress())
                            .gender(userDetailsDto.getGender())
                            .phoneNumber(userDetailsDto.getPhoneNumber())
                            .build();
                });

        user.setLastlogin(new Date());
        return userRepository.save(user);
    }


    public String generateToken(String username) {
        return jwtService.generateToken(customUserDetailsService.loadUserByUsername(username));
    }

    public boolean validateToken(String token) {
        if (!jwtService.isTokenExpired(token)) return getUserByEmail(jwtService.extractUsername(token)).isPresent();
        return false;
    }

}
