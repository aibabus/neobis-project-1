package com.shop.ShopApplication.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService implements EmailSender{
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    @Async
    public void send(String to, String email) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("nazaralievaibek003005@gmail.com");
            javaMailSender.send(mimeMessage);

            LOGGER.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send an email", e);
            throw new IllegalStateException("Failed to send an email");
        }
//        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//            helper.setText(email, true);
//            helper.setTo(to);
//            helper.setSubject("Confirm your email");
//            helper.setFrom("kakawkapipiwka@gmail.com");
//            mailSender.send(mimeMessage);
//        }catch (MessagingException e){
//            LOGGER.error("Failed to send an email", e);
//            throw new IllegalStateException("Failed to send ean email");
//        }

//        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//            helper.setText(email, true);
//            helper.setTo(to);
//            helper.setSubject("Confirm your email");
//            helper.setFrom("kakawkapipiwka@gmail.com");
//            mailSender.send(mimeMessage);
//
//            System.out.println("Done");
//
//        } catch (MessagingException e) {
//            LOGGER.error("Failed to send an email", e);
//            throw new IllegalStateException("Failed to send an email");
//        }

    }
}
