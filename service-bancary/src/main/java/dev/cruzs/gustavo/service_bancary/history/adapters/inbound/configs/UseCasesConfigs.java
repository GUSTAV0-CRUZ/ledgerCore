package dev.cruzs.gustavo.service_bancary.history.adapters.inbound.configs;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.CreateHistoryUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.FindAllHistoryByAccountIdUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.FindHistoryByIdUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.outbound.HistoryRepository;
import dev.cruzs.gustavo.service_bancary.history.application.services.CreateHistoryService;
import dev.cruzs.gustavo.service_bancary.history.application.services.FindAllHistoryByAccountIdService;
import dev.cruzs.gustavo.service_bancary.history.application.services.FindHistoryByIdService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfigs {
  @Bean
  public CreateHistoryUseCase createHistoryUseCase(HistoryRepository historyRepository) {
    return new CreateHistoryService(historyRepository);
  }

  @Bean
  public FindAllHistoryByAccountIdUseCase findAllHistoryByAccountIdUseCase(HistoryRepository historyRepository) {
    return new FindAllHistoryByAccountIdService(historyRepository);
  }

  @Bean
  public FindHistoryByIdUseCase findHistoryByIdUseCase(HistoryRepository historyRepository) {
    return new FindHistoryByIdService(historyRepository);
  }
}
