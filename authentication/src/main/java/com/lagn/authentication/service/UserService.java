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
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public Optional<UserCredentialDto> getUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile).map(user -> {
            return UserCredentialDto.builder().username(user.getMobile()).password(user.getPassword()).build();
        });
    }


    public Users createUser(UserDetailsDto userDetailsDto) throws SQLException {
        if (userDetailsDto.getMobile().length() != 10) throw new SQLException("mobile number is not valid");
        if (userDetailsDto.getPassword().isEmpty() || userDetailsDto.getPassword().isBlank())
            throw new SQLException("password is required");
        Users user = Users.builder().mobile(userDetailsDto.getMobile()).email(userDetailsDto.getEmail()).name(userDetailsDto.getName()).password(passwordEncoder.encode(userDetailsDto.getPassword())).whatsAppNo(userDetailsDto.getWhatsAppNo()).build();
        return userRepository.save(user);
    }


    public String generateToken(String username) {
        return jwtService.generateToken(customUserDetailsService.loadUserByUsername(username));
    }

    public boolean validateToken(String token) {
        if (!jwtService.isTokenExpired(token)) return getUserByMobile(jwtService.extractUsername(token)).isPresent();
        return false;
    }

    public void isUserExist(String username) throws UsernameAlreadyExistException {
        if (userRepository.findByMobile(username).isPresent()) {
            throw new UsernameAlreadyExistException();
        }
    }
}
