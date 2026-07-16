package dev.cruzs.gustavo.auth_service.adapters.configs;

import dev.cruzs.gustavo.auth_service.application.ports.in.CreateUserUseCase;
import dev.cruzs.gustavo.auth_service.application.ports.out.Hash;
import dev.cruzs.gustavo.auth_service.application.ports.out.UserRepository;
import dev.cruzs.gustavo.auth_service.application.services.CreateUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {
  @Bean
  public CreateUserUseCase createUserUseCase(UserRepository userRepository, Hash hash) {
    return new CreateUserService(userRepository, hash);
  }
}
