package com.mayank.skillsell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String to, String subject, String body){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(subject);
            mailMessage.setTo(to);
            mailMessage.setFrom("skillsell@demomailtrap.com");
            mailMessage.setText(body);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
