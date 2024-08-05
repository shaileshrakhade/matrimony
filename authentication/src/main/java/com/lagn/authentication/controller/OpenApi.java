package com.lagn.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openapi/")
public class OpenApi{
    @GetMapping("welcome")
    public String welcome(){
        return "Welcome page from Open APi";
    }
}
