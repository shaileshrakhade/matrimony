package com.sr.authentication.service.email;

import jakarta.mail.MessagingException; 
public interface Email {
    void sendEmail(String subject, String body, String attachmentName, String attachmentPath, String... email) throws MessagingException;

    void sendEmail(String subject, String body, String... email);
}
