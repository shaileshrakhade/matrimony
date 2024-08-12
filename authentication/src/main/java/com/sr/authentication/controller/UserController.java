package com.sr.authentication.controller;

import com.sr.authentication.customExceptions.dto.TokenDto;
import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;
import com.sr.authentication.customExceptions.exceptions.InvalidTokenException;
import com.sr.authentication.dao.UserCredentialDto;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.enums.Provider;
import com.sr.authentication.enums.Role;
import com.sr.authentication.service.UserService;
import com.sr.authentication.service.OtpVerification;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final OtpVerification otpVerification;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto registerUser(@RequestBody UserDetailsDto userDetails) throws SQLException, IOException {
        userDetails.setProvider(Provider.LOCAL);
        userDetails.setRoles(Role.USER);
        return userService.createUser(userDetails);
    }

    @PutMapping("active")
    @ResponseStatus(HttpStatus.OK)
    public String activeUser(@RequestBody UserCredentialDto userCredentialDto, HttpServletRequest request) throws InvalidTokenException {
        String token = request.getHeader("Authorization");
        final String jwtToken = token.substring(7);
        String username = userService.valueFromToken(jwtToken, "username");
        if (userCredentialDto.getUserName().equals(username)) {
            userCredentialDto.setUserName(username);
            userCredentialDto.setActive(true);
            return "user is activated :: "
                    + userService.activeUser(userCredentialDto);
        } else {
            throw new InvalidTokenException("Token is not valid");
        }
    }

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto updateUser(HttpServletRequest request) throws InvalidTokenException {
        String token = request.getHeader("Authorization");
        final String jwtToken = token.substring(7);
        String username = userService.valueFromToken(jwtToken, "username");
        UserDetailsDto userCredentialDto = userService.getUserByUserName(username).orElseThrow(() -> new InvalidTokenException("User Not valid"));
        return userService.updateUser(userCredentialDto);
    }

    @GetMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(@RequestBody UserCredentialDto userCredential) throws UsernameNotFoundException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userCredential.getUserName(), userCredential.getPassword()));
        if (authentication.isAuthenticated()) {
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(userService.generateToken(userCredential.getUserName()));
            tokenDto.setMessage("Login Successful.");
            return tokenDto;
        } else {
            throw new UsernameNotFoundException("User is not Authenticate");
        }
    }

    @GetMapping("send-otp")
    @ResponseStatus(HttpStatus.OK)
    public String senOtp(@RequestBody UserCredentialDto userCredentialDto) {
        UserDetailsDto userDetailsDto = userService.getUserByUserName(userCredentialDto.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!userDetailsDto.getEmailId().isEmpty()) {
            otpVerification.generateOtp(userCredentialDto.getUserName());
            otpVerification.sendOtpOnMail(userDetailsDto.getFullName(),userDetailsDto.getEmailId());
            return "OTP Send Successfully";
        } else {
            return "email Id was not register with this username";
        }
    }

    @GetMapping("forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto forgoPassword(@RequestBody UserCredentialDto userCredentialDto) throws InvalidOtpException {
        if (otpVerification.validateOtp(userCredentialDto.getUserName(),userCredentialDto.getOtp())) {
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(userService.generateToken(userCredentialDto.getUserName()));
            tokenDto.setMessage("OTP Validation Success");
            return tokenDto;
        } else {
            throw new InvalidOtpException("Otp Is not Valid");
        }
    }

    @PutMapping("update-password")
    @ResponseStatus(HttpStatus.OK)
    public String updatePassword(@RequestBody UserCredentialDto userCredentialDto, HttpServletRequest request) throws InvalidTokenException {
        String token = request.getHeader("Authorization");
        final String jwtToken = token.substring(7);
        String username = userService.valueFromToken(jwtToken, "username");
        if (userCredentialDto.getUserName().equals(username)) {
            userCredentialDto.setUserName(username);
            return "password successfully update :: "
                    + userService.passwordUpdate(userCredentialDto);
        } else {
            throw new InvalidTokenException("Token is not valid");
        }
    }

    @GetMapping("validate-token")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto refreshToken(@RequestParam("token") String jwtToken) throws InvalidTokenException {
        TokenDto tokenDto = new TokenDto();
//        String token = request.getHeader("Authorization");
//        final String jwtToken = token.substring(7);
        String username = userService.valueFromToken(jwtToken, "username");
        if (userService.validateToken(jwtToken)) {
            tokenDto.setToken(userService.generateToken(username));
            tokenDto.setMessage("token is validate and refresh");
            return tokenDto;
        } else {
            throw new InvalidTokenException("Security token is not valid");
        }

    }


    @DeleteMapping("delete/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean deleteUser(@PathVariable Long userId, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        final String jwtToken = token.substring(7);
        String usernameFromToken = userService.valueFromToken(jwtToken, "username");
        String usernameFromId = userService.getUserByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("user not fpund")).getUserName();

        if (usernameFromId.equals(usernameFromToken)) {
            return userService.deleteUser(userId);
        } else {
            //admin can delete
            return false;
        }
    }
}
