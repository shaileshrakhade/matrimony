package com.sr.authentication.controller;

import com.sr.authentication.dao.TokenDto;
import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;
import com.sr.authentication.customExceptions.exceptions.InvalidTokenException;
import com.sr.authentication.customExceptions.exceptions.PhoneNumberAlreadyExistException;
import com.sr.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import com.sr.authentication.dao.UserCredentialDto;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.enums.Provider;
import com.sr.authentication.enums.Role;
import com.sr.authentication.service.UserService;
import com.sr.authentication.service.otp.EmailOtpImpl;
import com.sr.authentication.util.jwt.JwtClaims;
import com.sr.authentication.util.jwt.JwtTokenGenerate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/")

public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtClaims jwtClaims;
    private final EmailOtpImpl emailOtp;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtClaims jwtClaims, EmailOtpImpl emailOtp) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtClaims = jwtClaims;
        this.emailOtp = emailOtp;
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto registerUser(@RequestBody UserDetailsDto userDetails) throws UsernameAlreadyExistException {
        userDetails.setProvider(Provider.LOCAL);
        userDetails.setRoles(Role.USER);
        return userService.createUser(userDetails);
    }

    //set the password of user & active the account
    @PutMapping("set-password")
    @ResponseStatus(HttpStatus.OK)
    public String activeUser(@RequestBody UserCredentialDto userCredentialDto, HttpServletRequest request) throws InvalidTokenException {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));

        if (userCredentialDto.getUserName().equals(username)) {
            userCredentialDto.setUserName(username);
            userCredentialDto.setActive(true);
            return "user is activated :: "
                    + userService.activeUser(userCredentialDto);
        } else {
            throw new InvalidTokenException("Token is not valid");
        }
    }

    @GetMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(@RequestBody UserCredentialDto userCredential) throws UsernameNotFoundException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userCredential.getUserName(), userCredential.getPassword()));
        if (authentication.isAuthenticated()) {
            UserDetailsDto userDetailsDto = userService.getUserByUserName(userCredential.getUserName());
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(userService.generateToken(userDetailsDto.getUserName()));
            tokenDto.setMessage("Login Successful.");
            return tokenDto;
        } else {
            throw new UsernameNotFoundException("User is not Authenticate");
        }
    }

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto updateUser(@RequestBody UserDetailsDto userDetailsDto, HttpServletRequest request) throws PhoneNumberAlreadyExistException, InvalidTokenException {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (username.equals(userDetailsDto.getUserName()))
            return userService.updateUser(userDetailsDto, username);
        else
            throw new InvalidTokenException("Token is invalid");
    }


    @GetMapping("send-otp")
    @ResponseStatus(HttpStatus.OK)
    public String senOtp(@RequestBody UserCredentialDto userCredentialDto) {
        UserDetailsDto userDetailsDto = userService.getUserByUserName(userCredentialDto.getUserName());
        if (!userDetailsDto.getEmailId().isEmpty()) {
            String otpNumber = emailOtp.generateOtp(userCredentialDto.getUserName());
//            emailOtp.setOtp(otpNumber);
            emailOtp.setEmail(userDetailsDto.getEmailId());
            emailOtp.setUsername(userDetailsDto.getFullName());
            boolean isSend = emailOtp.senOtp(otpNumber);
            return "OTP Send Successfully";
        } else {
            return "email Id was not register with this username";
        }
    }

    @GetMapping("forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto forgoPassword(@RequestParam("username") String username, @RequestParam("otp") String otp) throws InvalidOtpException, InvalidTokenException {
        if (emailOtp.validateOtp(username, otp)) {
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(userService.generateToken(username));
            tokenDto.setMessage("OTP Validation Success");
            return tokenDto;
        } else {
            throw new InvalidOtpException("Otp Is not Valid");
        }
    }

    @PutMapping("update-password")
    @ResponseStatus(HttpStatus.OK)
    public String updatePassword(@RequestBody UserCredentialDto userCredentialDto, HttpServletRequest request) throws InvalidTokenException {

        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (userCredentialDto.getUserName().equals(username)) {
            userCredentialDto.setUserName(username);
            return "password successfully update :: ";
        } else {
            throw new InvalidTokenException("Token is not valid");
        }
    }

    @GetMapping("validate-token")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto refreshToken(@RequestParam("token") String jwtToken) throws InvalidTokenException {
        TokenDto tokenDto = new TokenDto();
        String username = jwtClaims.extractUsername(jwtToken);
        if (userService.isTokenValid(jwtToken, username)) {
            tokenDto.setToken(userService.generateToken(username));
            tokenDto.setMessage("token is validate and refresh");
            return tokenDto;
        } else {
            throw new InvalidTokenException("Security token is not valid");
        }

    }

    @DeleteMapping("de-active/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean deleteUser(@PathVariable Long userId, HttpServletRequest request) {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        return userService.deActiveUser(userId, username);

    }
}
