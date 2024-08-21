package com.sr.authentication.service.otp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class OtpGeneratorImpl implements OtpGenerator {
    @Value("${custom.security.otp.code.length}")
    private int otpLength;

    @Override
    public String getOTP() {
        String numbers = "0123456789";
        // Using random method
        Random random_method = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append(numbers.charAt(random_method.nextInt(numbers.length())));
        }
        return otp.toString();
    }
}
