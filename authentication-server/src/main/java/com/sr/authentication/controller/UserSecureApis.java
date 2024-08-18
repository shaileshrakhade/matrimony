package com.sr.authentication.controller;

import com.sr.authentication.customExceptions.exceptions.InvalidTokenException;

import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.service.UserService;
import com.sr.authentication.util.jwt.CustomKeysEnums;
import com.sr.authentication.util.jwt.JwtClaims;
import com.sr.authentication.util.jwt.JwtTokenGenerate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/secure/")
@RequiredArgsConstructor
public class UserSecureApis {

    private final UserService userService;
    private final JwtClaims jwtClaims;
    private final JwtTokenGenerate jwtTokenGenerate;

    @GetMapping("welcome")
    @ResponseStatus(HttpStatus.OK)
    public String welcome() throws InvalidTokenException {
        return "Welcome to User Login page";
    }

    @GetMapping("value-from-token/{key}")
    @ResponseStatus(HttpStatus.OK)
    public String valueFromToken(@PathVariable("key") CustomKeysEnums key, HttpServletRequest request) throws InvalidTokenException {
        return jwtClaims.valueFromToken(request.getHeader(HttpHeaders.AUTHORIZATION), key);
    }
    @GetMapping("roles")
    @ResponseStatus(HttpStatus.OK)
    public List<String> valueFromToken(HttpServletRequest request) throws InvalidTokenException {
        return jwtClaims.extractRoles(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    @GetMapping("me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object currentUser = authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("profile")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto profile(HttpServletRequest request) {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        return userService.getUserByUserName(username);
    }


}
