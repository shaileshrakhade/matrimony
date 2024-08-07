package com.lagn.authentication.service;

import com.lagn.authentication.dao.UserCredentialDto;
import com.lagn.authentication.dao.UserDetailsDto;
import com.lagn.authentication.model.Users;
import com.lagn.authentication.repo.UserRepository;
import com.lagn.authentication.securityConfig.service.CustomUserDetailsService;
import com.lagn.authentication.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public Optional<UserCredentialDto> getUserByUserName(String username) {
        return userRepository.findByUserName(username).map(user -> {
            return UserCredentialDto.builder().userName(user.getUserName()).password(user.getPassword()).build();
        });
    }

    public Users passwordUpdate(UserCredentialDto UserCredentialDto) {
        Optional<Users> users = userRepository.findByUserName(UserCredentialDto.getUserName());
        if (users.isPresent()) {
            Users user = Users.builder()
                    .password(passwordEncoder.encode(UserCredentialDto.getPassword()))
                    .build();
            return userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User Name not exist");
        }
    }

    public Users updateUser(UserDetailsDto userDetailsDto) {

        Optional<Users> users = userRepository.findByUserName(userDetailsDto.getUserName());
        if (users.isPresent()) {
            Users user = Users.builder()
                    .id(users.get().getId())
                    .userName(userDetailsDto.getUserName())
                    .emailId(userDetailsDto.getEmailId())
                    .phoneNumber(userDetailsDto.getPhoneNumber())
                    .fullName(userDetailsDto.getFullName())
                    .whatsAppNo(userDetailsDto.getWhatsAppNo())
                    .birthDate(userDetailsDto.getBirthDate())
                    .address(userDetailsDto.getAddress())
                    .gender(userDetailsDto.getGender())
                    .updateOn(new Date())
                    .build();
            return userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User Name not exist");
        }

    }

    public Users createUser(UserDetailsDto userDetailsDto) throws SQLException {
        if (userDetailsDto.getPhoneNumber().length() != 10) throw new SQLException("Mobile Number is not valid");
        if (userDetailsDto.getEmailId().isEmpty() || userDetailsDto.getEmailId().isBlank())
            throw new SQLException("Email is required");
        if (userRepository.findByUserName(userDetailsDto.getUserName()).isPresent()) {
            throw new SQLException("Duplicate entry : account is already exist");
        } else {
            Users user = Users.builder()
                    .userName(userDetailsDto.getUserName())
                    .emailId(userDetailsDto.getEmailId())
                    .phoneNumber(userDetailsDto.getPhoneNumber())
                    .fullName(userDetailsDto.getFullName())
//                    .password(passwordEncoder.encode(userDetailsDto.getPassword()))
                    .whatsAppNo(userDetailsDto.getWhatsAppNo())
                    .birthDate(userDetailsDto.getBirthDate())
                    .address(userDetailsDto.getAddress())
                    .gender(userDetailsDto.getGender())
                    .provider(userDetailsDto.getProvider())
                    .registerOn(new Date())
                    .build();
            return userRepository.save(user);
        }
    }


    public String generateToken(String username) {

        String token = jwtService.generateToken(customUserDetailsService.loadUserByUsername(username));
        Optional<Users> useroptional = userRepository.findByUserName(username);
        if (useroptional.isPresent()) {
            Users user = Users.builder()
                    .id(useroptional.get().getId())
                    .tokenGeneratorOn(new Date())
                    .build();
            userRepository.save(user);
        }
        return token;
    }

    public boolean validateToken(String token) {
        if (jwtService.isTokenExpired(token)) return getUserByUserName(jwtService.extractUsername(token)).isPresent();
        return false;
    }

}
