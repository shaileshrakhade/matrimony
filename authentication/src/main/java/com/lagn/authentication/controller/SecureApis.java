package com.lagn.authentication.controller;

import com.lagn.authentication.customExceptions.exceptions.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController("/secure")
@Slf4j
public class SecureApis {
    @GetMapping("")
    public String success() throws InvalidTokenException {
        try {
           return  "Login Successfully";
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InvalidTokenException();
        }

    }
}
