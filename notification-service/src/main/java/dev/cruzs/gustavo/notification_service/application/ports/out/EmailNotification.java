package dev.cruzs.gustavo.notification_service.application.ports.out;

public interface EmailNotification {
  public void sendEmail(String to, String subject, String text);
}
