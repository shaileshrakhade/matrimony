package com.lagn.authentication.controller;

import com.lagn.authentication.customExceptions.dto.TokenDto;
import com.lagn.authentication.customExceptions.exceptions.InvalidTokenException;
import com.lagn.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import com.lagn.authentication.dao.UserCredentialDto;
import com.lagn.authentication.dao.UserDetailsDto;
import com.lagn.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/login/")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("register")
    public String registerUser(@RequestBody UserDetailsDto userDetails) throws UsernameAlreadyExistException, SQLException {
        String username = userService.createUser(userDetails).getMobile();
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

    @GetMapping("token-validate")
    public boolean validateToken(@RequestParam("token") String token) throws InvalidTokenException {
        try {
            return userService.validateToken(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InvalidTokenException();
        }

    }
}
