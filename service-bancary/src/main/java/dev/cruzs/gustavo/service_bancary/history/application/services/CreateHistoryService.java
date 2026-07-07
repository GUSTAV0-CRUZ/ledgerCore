package dev.cruzs.gustavo.service_bancary.history.application.services;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.CreateHistoryUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.CreateHistoryCommand;
import dev.cruzs.gustavo.service_bancary.history.application.ports.outbound.HistoryRepository;
import dev.cruzs.gustavo.service_bancary.history.domain.History;

import java.time.LocalDateTime;

public class CreateHistoryService implements CreateHistoryUseCase {
  private final HistoryRepository historyRepository;

  public CreateHistoryService(HistoryRepository historyRepository) {
    this.historyRepository = historyRepository;
  }

  @Override
  public History execute(CreateHistoryCommand command) {
    History history = History.create(
        command.accountId(),
        command.destinataryName(),
        command.institutionName(),
        LocalDateTime.now()
    );

    return historyRepository.save(history);
  }
}
