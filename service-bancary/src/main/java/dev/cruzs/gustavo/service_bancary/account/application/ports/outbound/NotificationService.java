package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound;

public interface NotificationService {
  void sendEmail(String to,  String subject, String text);
}
