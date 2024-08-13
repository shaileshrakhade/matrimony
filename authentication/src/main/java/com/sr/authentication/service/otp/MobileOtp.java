package com.sr.authentication.service.otp;

import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;

public class MobileOtp implements Otp {

    @Override
    public boolean senOtp(String otp) {
        return false;
    }

    @Override
    public String generateOtp(String username) {
        return "";
    }

    @Override
    public boolean validateOtp(String username, String otp) throws InvalidOtpException {
        return false;
    }
}
