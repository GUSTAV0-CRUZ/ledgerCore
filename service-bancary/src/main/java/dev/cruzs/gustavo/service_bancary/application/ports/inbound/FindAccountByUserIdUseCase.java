package dev.cruzs.gustavo.service_bancary.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.FindAccountByUserIdCommand;
import dev.cruzs.gustavo.service_bancary.domain.Account;

public interface FindAccountByUserIdUseCase {
  Account execute(FindAccountByUserIdCommand command);
}
