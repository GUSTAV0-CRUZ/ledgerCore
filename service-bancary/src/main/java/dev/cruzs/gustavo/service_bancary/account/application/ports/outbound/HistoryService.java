package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.CreateHistoryCommand;

public interface HistoryService {
  void create(CreateHistoryCommand createHistoryCommand);
}
