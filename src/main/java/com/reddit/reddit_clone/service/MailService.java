package com.reddit.reddit_clone.service;

import com.reddit.reddit_clone.exception.ActivationMailSendException;
import com.reddit.reddit_clone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    void sendEmail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("admin@reddit.com");
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.builder(notificationEmail.getBody()));
            messageHelper.setTo(notificationEmail.getRecipient());
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Activation email sent successfully!");
        } catch (MailException e) {
            log.error("error occurred when sending activation mail");
            throw new ActivationMailSendException("Exception occurred when sending email to " + notificationEmail.getRecipient(), e);
        }
    }

}
