package dev.cruzs.gustavo.notification_service.adapters.out;

import dev.cruzs.gustavo.notification_service.application.ports.out.EmailNotification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationAdapter implements EmailNotification {
  private final JavaMailSender javaMailSender;

  public EmailNotificationAdapter(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Override
  public void sendEmail(String to, String subject, String text) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(to);
    mailMessage.setSubject(subject);
    mailMessage.setText(text);

    this.javaMailSender.send(mailMessage);
  }
}
