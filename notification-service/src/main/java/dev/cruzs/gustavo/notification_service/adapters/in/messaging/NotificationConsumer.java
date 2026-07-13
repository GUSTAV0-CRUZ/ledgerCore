package dev.cruzs.gustavo.notification_service.adapters.in.messaging;

import dev.cruzs.gustavo.notification_service.adapters.in.messaging.dtos.SendEmailRequest;
import dev.cruzs.gustavo.notification_service.application.ports.in.SendEmailUseCase;
import dev.cruzs.gustavo.notification_service.application.ports.in.commands.SendEmailCommand;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class NotificationConsumer {
  private final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);
  private final SendEmailUseCase sendEmailUseCase;

  public NotificationConsumer(SendEmailUseCase sendEmailUseCase) {
    this.sendEmailUseCase = sendEmailUseCase;
  }

  public void sendEmail(@Valid SendEmailRequest sendEmailRequest) {
    SendEmailCommand sendEmailCommand = new SendEmailCommand(
        sendEmailRequest.to(),
        sendEmailRequest.subject(),
        sendEmailRequest.text()
    );
    sendEmailUseCase.execute(sendEmailCommand);

    logger.info("Send email to: ({}) with successfully", sendEmailRequest.to());
  }
}
