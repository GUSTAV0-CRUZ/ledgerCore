package dev.cruzs.gustavo.notification_service.application.services;

import dev.cruzs.gustavo.notification_service.application.ports.in.SendEmailUseCase;
import dev.cruzs.gustavo.notification_service.application.ports.in.commands.SendEmailCommand;
import dev.cruzs.gustavo.notification_service.application.ports.out.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendEmailService implements SendEmailUseCase {
  private final Logger logger = LoggerFactory.getLogger(SendEmailService.class);
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

    logger.info("Send email to: ({})", sendEmailCommand.to());
  }
}
