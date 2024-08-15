package com.sr.authentication.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
@Async
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    //send email to  multiple emails with attachment
    @Override
    public void sendEmail(String subject, String body, String attachmentName, String attachmentPath, String... email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject(subject);
        //HTML BODY
        message.setContent(body, "text/html; charset=utf-8");
        // Attachment
        FileSystemResource file = new FileSystemResource(new File(attachmentPath));
        helper.addAttachment(attachmentName, file);//image will be sent by this name

        mailSender.send(message);
    }

    //send email to  single email
    @Override
    public void sendEmail(String subject, String body, String... email) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            //HTML BODY
            message.setContent(body, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error in sendEmail ::" + e);
        }
    }
}
