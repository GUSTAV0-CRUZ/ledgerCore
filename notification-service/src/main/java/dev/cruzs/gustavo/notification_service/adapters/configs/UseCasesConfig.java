package dev.cruzs.gustavo.notification_service.adapters.configs;

import dev.cruzs.gustavo.notification_service.application.ports.in.SendEmailUseCase;
import dev.cruzs.gustavo.notification_service.application.ports.out.EmailNotification;
import dev.cruzs.gustavo.notification_service.application.services.SendEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {
  @Bean
  public SendEmailUseCase sendEmailUseCase(EmailNotification emailNotification) {
    return new SendEmailService(emailNotification);
  }
}
