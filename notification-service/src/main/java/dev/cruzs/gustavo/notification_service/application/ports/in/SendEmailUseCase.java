package dev.cruzs.gustavo.notification_service.application.ports.in;

import dev.cruzs.gustavo.notification_service.application.ports.in.commands.SendEmailCommand;

public interface SendEmailUseCase {
  public void execute(SendEmailCommand sendEmailCommand);
}
