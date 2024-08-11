package com.lagn.authentication.util;

import com.lagn.authentication.customExceptions.exceptions.InvalidOtpException;

public interface OtpVerification {

    void generateOtp(String username);

    default void sendOtpOnMail(String username,String email) {};

    default void sendOtpOnSMS(String username,String mobile) {};

    boolean validateOtp(String username, String otp) throws InvalidOtpException;

}
