package dev.cruzs.gustavo.service_bancary.history.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.FindHistoryByIdCommand;
import dev.cruzs.gustavo.service_bancary.history.domain.History;

public interface FindHistoryByIdUseCase {
  History execute(FindHistoryByIdCommand command);
}
