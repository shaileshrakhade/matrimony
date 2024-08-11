package com.lagn.authentication.controller;

import com.lagn.authentication.service.EmailService;
import com.lagn.authentication.util.OtpVerification;
import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@RequestMapping("/openapi/")
@Slf4j
public class OpenApi {
    @Autowired
    private OtpVerification otpVerification;

    @GetMapping("welcome")
    public String welcome() {

        return "Welcome page from Open APi";
    }

    @GetMapping("error")
    public void error(@RequestParam("error") String error) throws Exception {
        throw new Exception(error);
    }

    @GetMapping("send-otp")
    public String senOtp() throws MessagingException {
//        otpVerification.sendOtp("shailesh15.sr@gmail.com");
        return "email send successfully";
    }
}
