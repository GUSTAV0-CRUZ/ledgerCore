package dev.cruzs.gustavo.service_bancary.account.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public interface WithdrawAccountUseCase {
  Account execute(WithdrawAccountCommand command);
}
