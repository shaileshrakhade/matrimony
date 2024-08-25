package com.sr.authentication.service.otp;

import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;
import com.sr.authentication.customExceptions.exceptions.InvalidTokenException;
import com.sr.authentication.dao.UserDetailsDto;
import com.sr.authentication.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Data
public class OtpServiceImpl implements OtpService {

    @Autowired
    private UserService userService;
    @Autowired
    private OtpGenerator otpGenerator;


    @Override
    public boolean senOtp(String otp) {
        return false;
    }

    @Override
    public String generateOtp(String username) {
        return userService.otpUpdate(username, otpGenerator.getOTP());
    }

    @Override
    public boolean validateOtp(String username, String otp) throws InvalidOtpException, InvalidTokenException {
        UserDetailsDto userDetailsDto = userService.getUserByUserName(username);
        if (userDetailsDto.getOtpExpiryOn().after(new Date()))
            throw new UsernameNotFoundException("OTP is Expire");
        return userDetailsDto.getOtp().equals(otp);
    }
}
