package com.lagn.authentication.service;

import com.lagn.authentication.util.Constants;
import com.lagn.authentication.util.EmailTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpByEmail extends Otp {
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailTemplate emailTemplate;

    @Override
    public void sendOtpOnMail(String username,String email) {
        log.info("OTP :: {}", super.otp);
        String body = emailTemplate.otpTemplate(username,super.otp);
        emailService.sendEmail(Constants.OTP_EMAIL_SUBJECT, body, email);
    }


}
