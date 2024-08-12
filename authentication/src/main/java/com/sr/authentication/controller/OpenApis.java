package com.sr.authentication.controller;

import com.sr.authentication.service.OtpVerification;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openapi/")
@Slf4j
public class OpenApis {
    @Autowired
    private OtpVerification otpVerification;

    @GetMapping("welcome")
    @ResponseStatus(HttpStatus.OK)
    public String welcome() {

        return "Welcome page from Open APi";
    }

    @GetMapping("error")
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public void error(@RequestParam("error") String error) throws Exception {
        throw new Exception(error);
    }

    @GetMapping("send-otp")
    @ResponseStatus(HttpStatus.OK)
    public String senOtp() throws MessagingException {
//        otpVerification.sendOtp("shailesh15.sr@gmail.com");
        return "email send successfully";
    }
}
