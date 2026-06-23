package dev.cruzs.gustavo.service_bancary.account.adapters.inbound.messaging.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.Optional;

@Configuration
public class RmqErrorHandler {
  private static final Logger logger = LoggerFactory.getLogger(RmqErrorHandler.class);

  @Bean
  @ServiceActivator(inputChannel = "errorChannel")
  public MessageHandler messageHandler() {
    return message -> {
      if (message.getPayload() instanceof MessagingException messagingException) {
        String reason = Optional.ofNullable(messagingException.getCause())
            .map(Throwable::getMessage)
            .orElse(messagingException.getMessage());

        String queue = Optional.ofNullable(messagingException.getFailedMessage())
            .map(msg -> msg.getHeaders().get("amqp_consumerQueue", String.class))
            .orElse("queue Unknown");

        logger.warn("Message of queue [{}], send for DLQ. Reason: [{}]", queue, reason);
        return;
      }

      logger.warn("Error in errorChannel: {}", message.getPayload());
    };
  }
}
