package ru.iteco.fmh.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.service.mail.notifier.Notifier;
import ru.iteco.fmh.service.mail.notifier.SendEmailNotifierContext;

@Service
@RequiredArgsConstructor
public class SendEmailNotifier implements Notifier<SendEmailNotifierContext> {

    private final JavaMailSender emailSender;

    @Async
    public void send(SendEmailNotifierContext notifierContext) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notifierContext.getToAddress());
        message.setFrom(notifierContext.getFromAddress());
        message.setSubject(notifierContext.getSubject());
        message.setText(notifierContext.getContent());

        emailSender.send(message);
    }
}