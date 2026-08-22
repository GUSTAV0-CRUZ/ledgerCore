package dev.cruzs.gustavo.service_bancary.account.adapters.configs;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.MovementRepositoryAdapter;
import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.repositories.MovementJpaRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.MovementRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceMovementConfig {
  @Bean
  public MovementRepository movementRepositoryAdapter(MovementJpaRepository movementJpaRepository) {
    return new MovementRepositoryAdapter(movementJpaRepository) {
    };
  }
}
