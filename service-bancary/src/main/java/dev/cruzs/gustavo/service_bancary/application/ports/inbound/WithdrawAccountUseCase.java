package dev.cruzs.gustavo.service_bancary.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.domain.Account;

public interface WithdrawAccountUseCase {
  Account execute(WithdrawAccountCommand command);
}
