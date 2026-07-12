package dev.cruzs.gustavo.notification_service.application.services;

import dev.cruzs.gustavo.notification_service.application.ports.in.SendEmailUseCase;
import dev.cruzs.gustavo.notification_service.application.ports.in.commands.SendEmailCommand;
import dev.cruzs.gustavo.notification_service.application.ports.out.EmailNotification;

public class SendEmailService implements SendEmailUseCase {
  private final EmailNotification emailNotification;

  public SendEmailService(EmailNotification emailNotification) {
    this.emailNotification = emailNotification;
  }

  @Override
  public void execute(SendEmailCommand sendEmailCommand) {
    this.emailNotification.sendEmail(
        sendEmailCommand.to(),
        sendEmailCommand.subject(),
        sendEmailCommand.text()
    );
  }
}
