package dev.cruzs.gustavo.service_bancary.history.adapters.inbound.configs;

import dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.HistoryRepositoryAdapter;
import dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.repositories.HistoryJpaRepository;
import dev.cruzs.gustavo.service_bancary.history.application.ports.outbound.HistoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
  @Bean
  public HistoryRepository historyRepository(HistoryJpaRepository historyJpaRepository) {
    return new HistoryRepositoryAdapter(historyJpaRepository);
  }
}
