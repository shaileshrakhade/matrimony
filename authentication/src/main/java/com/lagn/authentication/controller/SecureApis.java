package com.lagn.authentication.controller;

import com.lagn.authentication.customExceptions.exceptions.InvalidTokenException;

import com.lagn.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/")
@RequiredArgsConstructor
public class SecureApis {

    private final UserService userService;

    @GetMapping("welcome")
    public String welcome() throws InvalidTokenException {
        return "Welcome Login is Successfully";
    }

    @GetMapping("me")
    public ResponseEntity<Object> authenticatedUser() throws InvalidTokenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object currentUser = authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("profile")
    public ResponseEntity<Object> profile() throws InvalidTokenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object currentUser = authentication.getPrincipal();
        userService.getUserByUserName("");
        return ResponseEntity.ok(currentUser);
    }


}
