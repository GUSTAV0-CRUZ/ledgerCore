package dev.cruzs.gustavo.service_bancary.history.adapters.inbound.configs;

import dev.cruzs.gustavo.service_bancary.history.adapters.inbound.messaging.HistoryConsumer;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.CreateHistoryUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.CreateHistoryCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ConsumersConfig {
  @Bean
  public HistoryConsumer historyConsumer(CreateHistoryUseCase createHistoryUseCase) {
    return new HistoryConsumer(createHistoryUseCase);
  }

  @Bean
  public Consumer<CreateHistoryCommand> createHistoryConsumer(HistoryConsumer historyConsumer) {
    return historyConsumer.createHistoryConsumer();
  }
}
