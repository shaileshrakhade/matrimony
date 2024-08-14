package com.sr.authentication.controller;

import com.sr.authentication.customExceptions.exceptions.InvalidTokenException;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private UserService userService;

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

    @GetMapping("users")
    public List<UserDetailsDto> allUsers() {
        return userService.getAllUser();
    }
    @DeleteMapping("delete/{userId}/{username}")
    @ResponseStatus(HttpStatus.OK)
    public long deleteUser(@PathVariable() Long userId, @PathVariable String username) {
        return userService.deleteUser(userId, username);

    }
}
