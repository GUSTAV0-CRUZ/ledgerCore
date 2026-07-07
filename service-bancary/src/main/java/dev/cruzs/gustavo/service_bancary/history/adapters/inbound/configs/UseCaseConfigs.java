package dev.cruzs.gustavo.service_bancary.history.adapters.inbound.configs;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.CreateHistoryUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.outbound.HistoryRepository;
import dev.cruzs.gustavo.service_bancary.history.application.services.CreateHistoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfigs {
  @Bean
  public CreateHistoryUseCase createHistoryUseCase(HistoryRepository historyRepository) {
    return new CreateHistoryService(historyRepository);
  }
}
