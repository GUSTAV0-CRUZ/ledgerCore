package dev.cruzs.gustavo.service_bancary.account.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.FindAccountByUserIdCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public interface FindAccountByUserIdUseCase {
  Account execute(FindAccountByUserIdCommand command);
}
