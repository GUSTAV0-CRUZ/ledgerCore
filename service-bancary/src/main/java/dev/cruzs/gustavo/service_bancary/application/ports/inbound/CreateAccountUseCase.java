package dev.cruzs.gustavo.service_bancary.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.CreateAccountCommand;
import dev.cruzs.gustavo.service_bancary.domain.Account;

public interface CreateAccountUseCase {
  Account execute(CreateAccountCommand createAccountCommand);
}
