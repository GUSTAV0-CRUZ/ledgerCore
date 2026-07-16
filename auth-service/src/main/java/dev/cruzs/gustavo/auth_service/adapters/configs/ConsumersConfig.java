package dev.cruzs.gustavo.auth_service.adapters.configs;

import dev.cruzs.gustavo.auth_service.adapters.in.dtos.CreateUserRequestDto;
import dev.cruzs.gustavo.auth_service.adapters.in.messaging.UserConsumer;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ConsumersConfig {
  public Consumer<CreateUserRequestDto> createUserConsumer(UserConsumer userConsumer) {
    return userConsumer::createUserConsumer;
  }
}
