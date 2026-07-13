package dev.cruzs.gustavo.notification_service.adapters.configs;

import dev.cruzs.gustavo.notification_service.adapters.in.messaging.NotificationConsumer;
import dev.cruzs.gustavo.notification_service.adapters.in.messaging.dtos.SendEmailRequest;
import dev.cruzs.gustavo.notification_service.application.ports.in.commands.SendEmailCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ConsumersConfig {
  private final NotificationConsumer notificationConsumer;

  public ConsumersConfig(NotificationConsumer notificationConsumer) {
    this.notificationConsumer = notificationConsumer;
  }

  @Bean
  public Consumer<SendEmailRequest> sendEmailConsumer() {
    return this.notificationConsumer::sendEmail;
  }
}
