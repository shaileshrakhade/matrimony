package com.sr.authentication.service.otp;

import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MobileOtpImpl extends OtpServiceImpl {

    @Override
    public boolean senOtp(String otp) {
        log.info("Send OTP On Mobile:: {}", otp);
        return false;
    }
}
