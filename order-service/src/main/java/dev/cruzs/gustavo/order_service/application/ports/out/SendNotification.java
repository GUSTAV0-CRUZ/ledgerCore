package dev.cruzs.gustavo.order_service.application.ports.out;

public interface SendNotification {
  void sendEmail(String to, String subject, String text);
}
