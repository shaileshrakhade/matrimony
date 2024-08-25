package com.sr.authentication.service;

import com.sr.authentication.dao.TokenDto;
import com.sr.authentication.customExceptions.exceptions.PhoneNumberAlreadyExistException;
import com.sr.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import com.sr.authentication.dao.UserCredentialDto;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.enums.Role;
import com.sr.authentication.model.Users;
import com.sr.authentication.repo.UserRepository;
import com.sr.authentication.securityConfig.service.CustomUserDetailsService;
import com.sr.authentication.util.jwt.JwtClaims;
import com.sr.authentication.util.jwt.JwtTokenGenerate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtClaims jwtClaims;
    private final JwtTokenGenerate jwtTokenGenerate;
    private final CustomUserDetailsService customUserDetailsService;
    private final AsyncServices asyncServices;

    @Value("${custom.security.otp.expiry.time}")
    private long otpExpiryTime;


    @Override
    public Optional<UserCredentialDto> getUserCredentialDtoByUserName(String username) {
        return userRepository.findByUserNameOrPhoneNumber(username, username).map(user -> {
            return UserCredentialDto.builder()
                    .userName(user.getUserName())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .isActive(user.isActive())
                    .build();
        });
    }

    @Override
    public List<UserDetailsDto> getAllUser() {
        return userRepository.findAll().stream().map(users -> {
                    return UserDetailsDto.builder()
                            .id(users.getId())
                            .userName(users.getUserName())
                            .phoneNumber(users.getPhoneNumber())
                            .emailId(users.getEmailId())
                            .fullName(users.getFullName())
                            .updateOn(users.getUpdateOn())
                            .tokenGeneratorOn(users.getTokenGeneratorOn())
                            .provider(users.getProvider())
                            .otp(users.getOtp())
                            .otpExpiryOn(users.getOtpExpiryOn())
                            .roles(users.getRole())
                            .build();

                }
        ).toList();
    }

    @Override
    public UserDetailsDto getUserByUserId(Long id) {
        return userRepository.findById(id).map(users -> {
            return UserDetailsDto.builder()
                    .id(users.getId())
                    .userName(users.getUserName())
                    .phoneNumber(users.getPhoneNumber())
                    .emailId(users.getEmailId())
                    .fullName(users.getFullName())
                    .updateOn(users.getUpdateOn())
                    .tokenGeneratorOn(users.getTokenGeneratorOn())
                    .provider(users.getProvider())
                    .otp(users.getOtp())
                    .otpExpiryOn(users.getOtpExpiryOn())
                    .roles(users.getRole())
                    .build();

        }).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public UserDetailsDto getUserByUserName(String username) throws UsernameNotFoundException {
        return userRepository.findByUserNameOrPhoneNumber(username, username).map(users -> {
            return UserDetailsDto.builder()
                    .id(users.getId())
                    .userName(users.getUserName())
                    .phoneNumber(users.getPhoneNumber())
                    .emailId(users.getEmailId())
                    .fullName(users.getFullName())
                    .updateOn(users.getUpdateOn())
                    .tokenGeneratorOn(users.getTokenGeneratorOn())
                    .provider(users.getProvider())
                    .otp(users.getOtp())
                    .otpExpiryOn(users.getOtpExpiryOn())
                    .roles(users.getRole())
                    .build();
        }).orElseThrow(() -> new UsernameNotFoundException("User Not Exist"));
    }

    @Override
    public String activeUser(UserCredentialDto UserCredentialDto) {
        Users users = userRepository.findByUserName(UserCredentialDto.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User Name not exist"));
        users.setPassword(passwordEncoder.encode(UserCredentialDto.getPassword()));
        users.setActive(UserCredentialDto.isActive());
        log.debug("Async call save from activeUser!");
        asyncServices.save(users);
        return users.getUserName();
    }

    @Override
    public UserDetailsDto updateUser(UserDetailsDto userDetailsDto, String username) throws PhoneNumberAlreadyExistException {
        Users users = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not exist"));
        if (!userDetailsDto.getPhoneNumber().isEmpty())
            if (!userDetailsDto.getPhoneNumber().equals(users.getPhoneNumber()))
                if (userRepository.findByPhoneNumber(userDetailsDto.getPhoneNumber()).isPresent()) {
                    throw new PhoneNumberAlreadyExistException("Mobile Number already exist");
                }
        users.setEmailId(userDetailsDto.getEmailId());
        users.setPhoneNumber(userDetailsDto.getPhoneNumber());
        users.setFullName(userDetailsDto.getFullName());
        users.setUpdateOn(new Date());
        log.debug("Async call save from updateUser!");
        asyncServices.save(users);
        return UserDetailsDto.builder()
                .id(users.getId())
                .userName(users.getUserName())
                .phoneNumber(users.getPhoneNumber())
                .emailId(users.getEmailId())
                .fullName(users.getFullName())
                .updateOn(users.getUpdateOn())
                .tokenGeneratorOn(users.getTokenGeneratorOn())
                .provider(users.getProvider())
                .otp(users.getOtp())
                .otpExpiryOn(users.getOtpExpiryOn())
                .roles(users.getRole())
                .build();
    }

    @Override
    public TokenDto createUser(UserDetailsDto userDetailsDto) throws UsernameAlreadyExistException {
        if (userRepository.findByUserNameOrPhoneNumber(userDetailsDto.getUserName(), userDetailsDto.getPhoneNumber()).isPresent()) {
            throw new UsernameAlreadyExistException("Account is already exist With this Mobile Number Or Email");
        } else {
            Users user = Users.builder()
                    .userName(userDetailsDto.getUserName())
                    .password(passwordEncoder.encode(userDetailsDto.getPassword()))
                    .emailId(userDetailsDto.getEmailId())
                    .phoneNumber(userDetailsDto.getPhoneNumber())
                    .fullName(userDetailsDto.getFullName())
                    .provider(userDetailsDto.getProvider())
                    .role(userDetailsDto.getRoles())
                    .registerOn(new Date())
                    .build();
            if (!userDetailsDto.getPassword().isEmpty())
                user.setActive(true);

            Users users = userRepository.save(user);
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(this.generateToken(users.getUserName()));
            tokenDto.setMessage("User Registered :: " + users.getUserName());
            return tokenDto;
        }
    }

    @Override
    public String otpUpdate(String username, String otp) {
        Users user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setOtp(otp);
        user.setOtpExpiryOn(new Date(System.currentTimeMillis() + otpExpiryTime));
        log.debug("Async call save from otpUpdate!");
        asyncServices.save(user);
        return user.getOtp();
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId, String username) {
        userRepository.findByIdAndUserName(userId, username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.debug("Async call deleteByIdAndUserName from deleteUser!");
        asyncServices.deleteByIdAndUserName(userId, username);
        return true;
    }

    @Override
    public boolean deActiveUser(Long userId, String username) {
        Users users = userRepository.findByIdAndUserName(userId, username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        users.setActive(false);
        log.debug("Async call save from deActiveUser!");
        asyncServices.save(users);
        return true;

    }

    @Override
    public UserDetailsDto markAdmin(Role role, String username) {
        Users users = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User Name not exist"));
        users.setRole(role);
        log.debug("Async call save from markAdmin!");
        asyncServices.save(users);
        return UserDetailsDto.builder()
                .id(users.getId())
                .userName(users.getUserName())
                .phoneNumber(users.getPhoneNumber())
                .emailId(users.getEmailId())
                .fullName(users.getFullName())
                .updateOn(users.getUpdateOn())
                .tokenGeneratorOn(users.getTokenGeneratorOn())
                .provider(users.getProvider())
                .otp(users.getOtp())
                .otpExpiryOn(users.getOtpExpiryOn())
                .roles(users.getRole())
                .build();

    }

    @Override
    public String generateToken(String username) {
        Users users = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User Name not exist"));
        users.setTokenGeneratorOn(new Date());
        log.debug("Async call save from generateToken!");
        asyncServices.save(users);
        //set roles in token
        return jwtTokenGenerate
                .generateToken(customUserDetailsService.loadUserByUsername(username));
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        return jwtTokenGenerate
                .isTokenValid(token, customUserDetailsService.loadUserByUsername(username));
    }
}
