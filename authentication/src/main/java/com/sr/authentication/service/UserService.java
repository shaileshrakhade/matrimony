package com.sr.authentication.service;

import com.sr.authentication.customExceptions.dto.TokenDto;
import com.sr.authentication.dao.UserCredentialDto;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.model.Users;
import com.sr.authentication.repo.UserRepository;
import com.sr.authentication.securityConfig.service.CustomUserDetailsService;
import com.sr.authentication.util.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Value("${custom.security.otp.expiry.time}")
    private long otpExpiryTime;


    public Optional<UserCredentialDto> getUserCredentialDtoByUserName(String username) {
        return userRepository.findByUserName(username).map(user -> {
            return UserCredentialDto.builder()
                    .userName(user.getUserName())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .isActive(user.isActive())
                    .build();
        });
    }

    public Optional<UserDetailsDto> getUserByUserId(Long id) {
        return userRepository.findById(id).map(users -> {
            return UserDetailsDto.builder()
                    .id(users.getId())
                    .userName(users.getUserName())
                    .phoneNumber(users.getPhoneNumber())
                    .emailId(users.getEmailId())
                    .fullName(users.getFullName())
                    .whatsAppNo(users.getWhatsAppNo())
                    .birthDate(users.getBirthDate())
                    .address(users.getAddress())
                    .gender(users.getGender())
                    .updateOn(users.getUpdateOn())
                    .tokenGeneratorOn(users.getTokenGeneratorOn())
                    .provider(users.getProvider())
                    .otp(users.getOtp())
                    .roles(users.getRole())
                    .build();

        });
    }

    public Optional<UserDetailsDto> getUserByUserName(String username) {
        return userRepository.findByUserName(username).map(users -> {
            return UserDetailsDto.builder()
                    .id(users.getId())
                    .userName(users.getUserName())
                    .phoneNumber(users.getPhoneNumber())
                    .emailId(users.getEmailId())
                    .fullName(users.getFullName())
                    .whatsAppNo(users.getWhatsAppNo())
                    .birthDate(users.getBirthDate())
                    .address(users.getAddress())
                    .gender(users.getGender())
                    .updateOn(users.getUpdateOn())
                    .tokenGeneratorOn(users.getTokenGeneratorOn())
                    .provider(users.getProvider())
                    .otp(users.getOtp())
                    .otpExpiryOn(users.getOtpExpiryOn())
                    .roles(users.getRole())
                    .build();
        });
    }

    public String passwordUpdate(UserCredentialDto UserCredentialDto) {
        Optional<Users> useroptional = userRepository.findByUserName(UserCredentialDto.getUserName());
        if (useroptional.isPresent()) {
            Users user = useroptional.get();
            user.setPassword(passwordEncoder.encode(UserCredentialDto.getPassword()));
            return userRepository.save(user).getUserName();
        } else {
            throw new UsernameNotFoundException("User Name not exist");
        }
    }

    public String activeUser(UserCredentialDto UserCredentialDto) {
        Optional<Users> useroptional = userRepository.findByUserName(UserCredentialDto.getUserName());
        if (useroptional.isPresent()) {
            Users user = useroptional.get();
            user.setPassword(passwordEncoder.encode(UserCredentialDto.getPassword()));
            user.setActive(UserCredentialDto.isActive());
            return userRepository.save(user).getUserName();
        } else {
            throw new UsernameNotFoundException("User Name not exist");
        }
    }

    public UserDetailsDto updateUser(UserDetailsDto userDetailsDto) {

        Optional<Users> usersOptional = userRepository.findByUserName(userDetailsDto.getUserName());

        Users user = usersOptional.orElseThrow(() -> new UsernameNotFoundException("User Name not exist"));
        //user.setUserName(userDetailsDto.getUserName());
        user.setEmailId(userDetailsDto.getEmailId());
        user.setPhoneNumber(userDetailsDto.getPhoneNumber());
        user.setFullName(userDetailsDto.getFullName());
        user.setWhatsAppNo(userDetailsDto.getWhatsAppNo());
        user.setBirthDate(userDetailsDto.getBirthDate());
        user.setAddress(userDetailsDto.getAddress());
        user.setGender(userDetailsDto.getGender());
        user.setUpdateOn(new Date());
        user.setOtp(userDetailsDto.getOtp());
        user = userRepository.save(user);
        return UserDetailsDto.builder()
                .userName(user.getUserName())
                .phoneNumber(user.getPhoneNumber())
                .emailId(user.getEmailId())
                .fullName(user.getFullName())
                .whatsAppNo(user.getWhatsAppNo())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .gender(user.getGender())
                .updateOn(user.getUpdateOn())
                .tokenGeneratorOn(user.getTokenGeneratorOn())
                .provider(user.getProvider()).build();


    }

    public TokenDto createUser(UserDetailsDto userDetailsDto) throws SQLException {
//
        if (this.getUserByUserName(userDetailsDto.getUserName()).isPresent()) {
            throw new SQLException("Duplicate entry : account is already exist");
        } else {
            Users user = Users.builder()
                    .userName(userDetailsDto.getUserName())
                    .emailId(userDetailsDto.getEmailId())
                    .phoneNumber(userDetailsDto.getPhoneNumber())
                    .fullName(userDetailsDto.getFullName())
                    .whatsAppNo(userDetailsDto.getWhatsAppNo())
                    .birthDate(userDetailsDto.getBirthDate())
                    .address(userDetailsDto.getAddress())
                    .gender(userDetailsDto.getGender())
                    .provider(userDetailsDto.getProvider())
                    .role(userDetailsDto.getRoles())
                    .registerOn(new Date())
                    .build();
            Users users = userRepository.save(user);
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(this.generateToken(users.getUserName()));
            tokenDto.setMessage("User Registered :: " + users.getUserName());
            return tokenDto;
        }
    }


    public String generateToken(String username) {
        Optional<Users> useroptional = userRepository.findByUserName(username);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("project", "marriage");
        String token = jwtService.generateToken(extraClaims, customUserDetailsService.loadUserByUsername(username));

        if (useroptional.isPresent()) {
            Users user = useroptional.get();
            user.setTokenGeneratorOn(new Date());
            userRepository.save(user);
        }
        return token;
    }

    public String otpUpdate(String username, String otp) {
        Users user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setOtp(otp);
        user.setOtpExpiryOn(new Date(System.currentTimeMillis() + otpExpiryTime));
        return userRepository.save(user).getOtp();

    }

    public boolean validateToken(String token) {
        if (jwtService.isTokenExpired(token))
            return getUserByUserName(jwtService.extractUsername(token)).isPresent();
        return false;
    }

    public String valueFromToken(String token, String key) {

//        final String userEmail = jwtService.extractUsername(jwt);
        if (jwtService.isTokenExpired(token))
            return jwtService.extractClaim(token, Claims::getSubject);
        return null;
    }

    public boolean deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return true;
    }


}
