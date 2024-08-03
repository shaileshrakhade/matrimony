package com.lagn.authentication.controller;

import com.lagn.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import com.lagn.authentication.dao.UserDetailsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController("/secure")
public class SecureApis {
    @GetMapping()
    public String createUser() {
        return "User Authenticate successfully";
    }
}
