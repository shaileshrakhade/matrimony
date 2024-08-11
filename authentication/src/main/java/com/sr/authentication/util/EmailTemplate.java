package com.sr.authentication.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
@Slf4j
public class EmailTemplate {
    @Autowired
    private ResourceLoader resourceLoader;

    public String otpTemplate(String username, String otp) {

        try {
            Resource resource = resourceLoader.getResource("classpath:templates/otp.html");

            InputStream inputStream = resource.getInputStream();
            byte[] data = FileCopyUtils.copyToByteArray(inputStream);
            String content = new String(data, StandardCharsets.UTF_8);
            content = content.replace("{OTP_VERIFICATION_USERNAME}", username);
            content = content.replace("{OTP_VERIFICATION_CODE}", otp);
            return content;
        } catch (IOException e) {
            throw new RuntimeException("otp template not found ::" + e);
        }
    }
}
