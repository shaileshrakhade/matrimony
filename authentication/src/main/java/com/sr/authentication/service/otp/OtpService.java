package com.sr.authentication.service.otp;

import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;
import com.sr.authentication.customExceptions.exceptions.InvalidTokenException;

public interface OtpService {
    boolean senOtp(String otp);
    String generateOtp(String username);
    boolean validateOtp(String username, String otp) throws InvalidOtpException, InvalidTokenException;
}
