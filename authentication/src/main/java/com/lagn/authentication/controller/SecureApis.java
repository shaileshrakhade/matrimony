package com.lagn.authentication.controller;

import com.lagn.authentication.customExceptions.exceptions.InvalidTokenException;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/mrg/")
public class SecureApis {
    @GetMapping("welcome")
    public String success() throws InvalidTokenException {
        try {
           return  "Login Successfully";
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InvalidTokenException();
        }

    }
    @GetMapping("me")
    public ResponseEntity<Object> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object currentUser =  authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

//    @GetMapping("token-validate")
//    public boolean validateToken(@RequestParam("token") String token) throws InvalidTokenException {
//        try {
//            return userService.validateToken(token);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new InvalidTokenException();
//        }
//
//    }

}
