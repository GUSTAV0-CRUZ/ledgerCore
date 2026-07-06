package dev.cruzs.gustavo.service_bancary.history.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.CreateHistoryCommand;
import dev.cruzs.gustavo.service_bancary.history.domain.History;

public interface CreateHistoryUseCase {
  History execute(CreateHistoryCommand command);
}
