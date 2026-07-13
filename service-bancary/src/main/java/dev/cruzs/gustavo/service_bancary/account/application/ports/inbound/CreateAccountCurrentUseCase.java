package dev.cruzs.gustavo.service_bancary.account.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.CreateAccountCurrentCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public interface CreateAccountCurrentUseCase {
  Account execute(CreateAccountCurrentCommand createAccountCurrentCommand);
}
