package dev.cruzs.gustavo.service_bancary.account.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.CreateAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public interface CreateAccountUseCase {
  Account execute(CreateAccountCommand createAccountCommand);
}
