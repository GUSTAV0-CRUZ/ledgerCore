package dev.cruzs.gustavo.service_bancary.user.adapters.configs;

import dev.cruzs.gustavo.service_bancary.user.adapters.inbound.messages.UserConsumer;
import dev.cruzs.gustavo.service_bancary.user.adapters.inbound.messages.dtos.CreateUserDto;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.CreateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessagingUserConfig {
  @Bean
  public UserConsumer userConsumer(CreateUserUseCase createUserUseCase) {
    return new UserConsumer(createUserUseCase);
  }

  @Bean
  public Consumer<CreateUserDto> createUserConsumer(UserConsumer userConsumer) {
    return userConsumer.createUserConsumer();
  }
}
