package com.sr.authentication.service.otp;

import com.sr.authentication.service.email.EmailService;
import com.sr.authentication.service.email.EmailTemplates;
import com.sr.authentication.util.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@EqualsAndHashCode(callSuper = true)
@Service
@RequiredArgsConstructor
@Slf4j
@Data
public class EmailOtpImpl extends OtpServiceImpl {

    private  String username;
    private  String email;
    private final EmailTemplates emailTemplate;
    private final EmailService emailService;

    @Override
    public boolean senOtp(String otp) {
        log.info("Send OTP On Email:: {}", otp);
        String body = emailTemplate.otpTemplate(username, otp);
        emailService.sendEmail(Constants.OTP_EMAIL_SUBJECT, body, email);
        return true;
    }

}
