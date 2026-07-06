package dev.cruzs.gustavo.service_bancary.history.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.FindAllHistoryByAccountIdCommand;
import dev.cruzs.gustavo.service_bancary.history.domain.History;

import java.util.List;

public interface FindAllHistoryByAccountIdUseCase {
  List<History> execute(FindAllHistoryByAccountIdCommand command);
}
