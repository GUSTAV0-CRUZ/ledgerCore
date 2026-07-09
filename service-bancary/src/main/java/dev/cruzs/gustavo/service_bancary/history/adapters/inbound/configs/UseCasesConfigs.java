package dev.cruzs.gustavo.service_bancary.history.adapters.inbound.configs;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.CreateHistoryUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.FindAllByAccountIdAndYearMonthUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.FindHistoryByIdUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.outbound.HistoryRepository;
import dev.cruzs.gustavo.service_bancary.history.application.services.CreateHistoryService;
import dev.cruzs.gustavo.service_bancary.history.application.services.FindAllByAccountIdAndYearMonthService;
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
  public FindAllByAccountIdAndYearMonthUseCase findAllHistoryByAccountIdUseCase(HistoryRepository historyRepository) {
    return new FindAllByAccountIdAndYearMonthService(historyRepository);
  }

  @Bean
  public FindHistoryByIdUseCase findHistoryByIdUseCase(HistoryRepository historyRepository) {
    return new FindHistoryByIdService(historyRepository);
  }
}
