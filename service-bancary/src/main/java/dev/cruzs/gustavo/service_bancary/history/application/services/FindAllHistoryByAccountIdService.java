package dev.cruzs.gustavo.service_bancary.history.application.services;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.FindAllHistoryByAccountIdUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.FindAllHistoryByAccountIdCommand;
import dev.cruzs.gustavo.service_bancary.history.application.ports.outbound.HistoryRepository;
import dev.cruzs.gustavo.service_bancary.history.domain.History;

import java.util.List;

public class FindAllHistoryByAccountIdService implements FindAllHistoryByAccountIdUseCase {
  private final HistoryRepository historyRepository;

  public FindAllHistoryByAccountIdService(HistoryRepository historyRepository) {
    this.historyRepository = historyRepository;
  }

  @Override
  public List<History> execute(FindAllHistoryByAccountIdCommand command) {
    return historyRepository.findAllByAccountId(command.accountId());
  }
}
