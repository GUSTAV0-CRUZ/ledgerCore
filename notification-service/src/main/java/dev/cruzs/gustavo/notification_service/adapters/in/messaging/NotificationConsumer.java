package dev.cruzs.gustavo.notification_service.adapters.in.messaging;

import dev.cruzs.gustavo.notification_service.application.ports.in.SendEmailUseCase;
import dev.cruzs.gustavo.notification_service.application.ports.in.commands.SendEmailCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
  private final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);
  private final SendEmailUseCase sendEmailUseCase;

  public NotificationConsumer(SendEmailUseCase sendEmailUseCase) {
    this.sendEmailUseCase = sendEmailUseCase;
  }

  public void sendEmail(SendEmailCommand sendEmailCommand) {
    sendEmailUseCase.execute(sendEmailCommand);

    logger.info("Send email to: ({}) with successfully", sendEmailCommand.to());
  }
}
