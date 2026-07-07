package dev.cruzs.gustavo.service_bancary.history.adapters.inbound.messaging;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.CreateHistoryUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.CreateHistoryCommand;
import dev.cruzs.gustavo.service_bancary.history.domain.History;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class HistoryConsumer {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final CreateHistoryUseCase createHistoryUseCase;

  public HistoryConsumer(CreateHistoryUseCase createHistoryUseCase) {
    this.createHistoryUseCase = createHistoryUseCase;
  }

  public Consumer<CreateHistoryCommand> createHistoryConsumer() {
    return createHistoryCommand -> {
      History history = createHistoryUseCase.execute(createHistoryCommand);

      logger.info("Created history with id: {}", history.getId());
    };
  }
}
