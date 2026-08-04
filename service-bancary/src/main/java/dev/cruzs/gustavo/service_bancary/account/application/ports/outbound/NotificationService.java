package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.SendEmailCommand;

public interface NotificationService {
  void sendEmail(SendEmailCommand sendEmailCommand);
}
