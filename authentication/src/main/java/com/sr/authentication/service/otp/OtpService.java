package com.sr.authentication.service.otp;

import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class OtpService {
    @Value("${custom.security.otp.code.length}")
    private static int otpLength;


    public static String OTP() {
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
