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
public class AdminController {
    @GetMapping("welcome")
    public String welcome() {
        return "Welcome to Admin Page";
    }

    @GetMapping("mark-admin")
    public String markAdmin() {
        return "markAdmin to Admin Page";
    }

    @GetMapping("active-user")
    public String activeUser() {
        return "activeUser to Admin Page";
    }
}