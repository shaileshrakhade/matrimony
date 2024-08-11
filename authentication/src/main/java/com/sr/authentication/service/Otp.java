package com.sr.authentication.service;

import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.util.OtpVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public abstract class Otp implements OtpVerification {
    @Value("${custom.security.otp.code.length}")
    private int otpLength;
    @Autowired
    private UserService userService;
    public String otp;


    private void generator(String username) {
        otp = userService.otpUpdate(username, OTP(this.otpLength));
    }

    private boolean validate(String username, String otp) throws InvalidOtpException {
        UserDetailsDto userDetailsDto = userService.getUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (userDetailsDto.getOtpExpiryOn().after(new Date()))
            throw new UsernameNotFoundException("OTP is Expire");

        return userDetailsDto.getOtp().equals(otp);

    }

    private static String OTP(int len) {
        String numbers = "0123456789";
        // Using random method
        Random random_method = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < len; i++) {
            otp.append(numbers.charAt(random_method.nextInt(numbers.length())));
        }
        return otp.toString();
    }

    @Override
    public void generateOtp(String username) {
        this.generator(username);
    }

    @Override
    public boolean validateOtp(String username, String otp) throws InvalidOtpException {
        return validate(username, otp);
    }

}
