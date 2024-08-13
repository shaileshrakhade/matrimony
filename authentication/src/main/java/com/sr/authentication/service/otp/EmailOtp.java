package com.sr.authentication.service.otp;

import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.service.email.EmailService;
import com.sr.authentication.service.UserService;
import com.sr.authentication.util.Constants;
import com.sr.authentication.util.EmailTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailOtp implements Otp {

    private final String username;
    private final String email;
    private final EmailTemplate emailTemplate;
    private final OtpService otpService;
    private final EmailService emailService;
    private UserService userService;

    @Override
    public boolean senOtp(String otp) {
        log.info("OTP :: {}", otp);
        String body = emailTemplate.otpTemplate(username, otp);
        emailService.sendEmail(Constants.OTP_EMAIL_SUBJECT, body, email);
        return true;
    }

    @Override
    public String generateOtp(String username) {
        return userService.otpUpdate(username, OtpService.OTP());
    }

    @Override
    public boolean validateOtp(String username, String otp) throws InvalidOtpException {
        UserDetailsDto userDetailsDto = userService.getUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (userDetailsDto.getOtpExpiryOn().after(new Date()))
            throw new UsernameNotFoundException("OTP is Expire");

        return userDetailsDto.getOtp().equals(otp);
    }


}
