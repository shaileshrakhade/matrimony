package com.sr.authentication.service.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Slf4j
public class EmailTemplates {
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

            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

            content = content.replace("{DATE_SEND}", LocalDateTime.now().format(myFormatObj));
            return content;
        } catch (IOException e) {
            throw new RuntimeException("otp template not found ::" + e);
        }
    }
}
