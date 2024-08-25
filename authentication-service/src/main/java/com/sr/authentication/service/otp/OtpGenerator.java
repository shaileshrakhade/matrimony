package com.sr.authentication.service.otp;

import org.springframework.beans.factory.annotation.Value;

@FunctionalInterface
public interface OtpGenerator {
    String getOTP();
}
