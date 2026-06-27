package dev.cruzs.gustavo.service_bancary.user.adapters.configs;

import dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence.UserRepositoryAdapter;
import dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence.repository.UserJpaRepository;
import dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceUserConfig {
  @Bean
  public UserRepository userRepository(UserJpaRepository userJpaRepository) {
    return new UserRepositoryAdapter(userJpaRepository);
  }
}
