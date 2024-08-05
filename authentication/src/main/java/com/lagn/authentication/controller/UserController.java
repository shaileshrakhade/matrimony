package com.lagn.authentication.controller;

import com.lagn.authentication.customExceptions.dto.TokenDto;
import com.lagn.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import com.lagn.authentication.dao.UserCredentialDto;
import com.lagn.authentication.dao.UserDetailsDto;
import com.lagn.authentication.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth/")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("register")
    public String registerUser(@RequestBody UserDetailsDto userDetails) throws SQLException {
        String username = userService.createUser(userDetails).getEmail();
        return "User register successfully with username :: " + username;
    }

    @PostMapping("token")
    public TokenDto generateToken(@RequestBody UserCredentialDto userCredential) throws UsernameNotFoundException {
        TokenDto tokenDto=new TokenDto();
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userCredential.getUsername(), userCredential.getPassword()));
            if (authentication.isAuthenticated())
                tokenDto.setToken(userService.generateToken(userCredential.getUsername()));

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UsernameNotFoundException("Username not found");
        }
        return tokenDto;
    }
}
