package dev.cruzs.gustavo.service_bancary.user.adapters.configs;

import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.CreateUserUseCase;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.FindUserByIdUseCase;
import dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.Hashing;
import dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.UserRepository;
import dev.cruzs.gustavo.service_bancary.user.application.services.CreateUserService;
import dev.cruzs.gustavo.service_bancary.user.application.services.FindUserByIdService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesUserConfig {
  private final UserRepository userRepository;

  public UseCasesUserConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public CreateUserUseCase createUserUseCase(Hashing hashing) {
    return new CreateUserService(this.userRepository, hashing);
  }

  @Bean
  public FindUserByIdUseCase findUserByIdUseCase() {
    return new FindUserByIdService(this.userRepository);
  }
}
