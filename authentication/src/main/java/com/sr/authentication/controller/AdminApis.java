package com.sr.authentication.controller;

import com.sr.authentication.customExceptions.exceptions.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminApis {
    @GetMapping("welcome")
    public String welcome() {
        return "Welcome to Admin Page";
    }
}
