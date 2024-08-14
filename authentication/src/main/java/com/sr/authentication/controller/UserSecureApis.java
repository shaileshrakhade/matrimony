package com.sr.authentication.controller;

import com.sr.authentication.customExceptions.exceptions.InvalidTokenException;

import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.service.UserService;
import com.sr.authentication.util.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/")
@RequiredArgsConstructor
public class UserSecureApis {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("welcome")
    @ResponseStatus(HttpStatus.OK)
    public String welcome() throws InvalidTokenException {
        return "Welcome to User Login page";
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
        String username = jwtService.extractUsername(request.getHeader("Authorization"));
        return userService.getUserByUserName(username);
    }


}
