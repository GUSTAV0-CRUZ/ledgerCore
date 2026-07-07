package dev.cruzs.gustavo.service_bancary.history.application.services;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.FindHistoryByIdUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.FindHistoryByIdCommand;
import dev.cruzs.gustavo.service_bancary.history.application.ports.outbound.HistoryRepository;
import dev.cruzs.gustavo.service_bancary.history.domain.History;

public class FindHistoryByIdService implements FindHistoryByIdUseCase {
  private HistoryRepository historyRepository;

  @Override
  public History execute(FindHistoryByIdCommand command) {
    return historyRepository.findByIdOrNotFoundExceptions(command.id());
  }
}
