package com.lagn.authentication.controller;

import com.lagn.authentication.customExceptions.dto.TokenDto;
import com.lagn.authentication.customExceptions.exceptions.InvalidTokenException;
import com.lagn.authentication.dao.UserCredentialDto;
import com.lagn.authentication.dao.UserDetailsDto;
import com.lagn.authentication.enums.Provider;
import com.lagn.authentication.service.UserService;
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
    public String registerUser(@RequestBody UserDetailsDto userDetails, HttpServletResponse response) throws SQLException, IOException {
        userDetails.setProvider(Provider.LOCAL);
        String username = userService.createUser(userDetails).getUserName();
        response.sendRedirect("/user/update-password");
        return "User register successfully with username :: " + username;
    }

    @PostMapping("update-password")
    @ResponseStatus(HttpStatus.OK)
    public String registerUser(@RequestBody UserCredentialDto userCredentialDto) throws SQLException {
        String username = userService.passwordUpdate(userCredentialDto).getUserName();
        return "password successfully set with username :: " + username;
    }

    @GetMapping("validate-token")
    @ResponseStatus(HttpStatus.OK)
    public boolean validateToken(@RequestParam("token") String token) throws InvalidTokenException {
        try {
            return userService.validateToken(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InvalidTokenException();
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
