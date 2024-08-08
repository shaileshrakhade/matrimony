package com.lagn.authentication.controller;

import com.lagn.authentication.customExceptions.dto.TokenDto;
import com.lagn.authentication.customExceptions.exceptions.InvalidTokenException;
import com.lagn.authentication.dao.UserCredentialDto;
import com.lagn.authentication.dao.UserDetailsDto;
import com.lagn.authentication.enums.Provider;
import com.lagn.authentication.model.Users;
import com.lagn.authentication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("register")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto registerUser(@RequestBody UserDetailsDto userDetails, HttpServletResponse response) throws SQLException, IOException {
        userDetails.setProvider(Provider.LOCAL);
        return userService.createUser(userDetails);
    }

    @PostMapping("forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public String forgoPassword(@RequestBody UserCredentialDto userCredentialDto) {
        //OTP authentication need to be implementing
        if (userCredentialDto.getOtp().equals("0000"))
            return "password successfully set with username ::"
                    + userService.passwordUpdate(userCredentialDto).getUserName();

        return "otp is not valid";
    }

    @PostMapping("update-password")
    @ResponseStatus(HttpStatus.OK)
    public String updatePassword(@RequestBody UserCredentialDto userCredentialDto, HttpServletRequest request) throws InvalidTokenException {
        String token = request.getHeader("Authorization");
        final String jwtToken = token.substring(7);
        String username = userService.valueFromToken(jwtToken, "username");
        if (userCredentialDto.getUserName().equals(username)) {
            userCredentialDto.setUserName(username);
            return "password successfully set with username :: "
                    + userService.passwordUpdate(userCredentialDto).getUserName();
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


    @PostMapping("token")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto generateToken(@RequestBody UserCredentialDto userCredential) throws UsernameNotFoundException {
        TokenDto tokenDto = new TokenDto();
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userCredential.getUserName(), userCredential.getPassword()));
            if (authentication.isAuthenticated())
                tokenDto.setToken(userService.generateToken(userCredential.getUserName()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UsernameNotFoundException("Username not found");
        }
        return tokenDto;
    }
}
