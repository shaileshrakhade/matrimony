package com.sr.authentication.util;

import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;

public interface OtpVerification {

    void generateOtp(String username);

    default void sendOtpOnMail(String username,String email) {};

    default void sendOtpOnSMS(String username,String mobile) {};

    boolean validateOtp(String username, String otp) throws InvalidOtpException;

}
