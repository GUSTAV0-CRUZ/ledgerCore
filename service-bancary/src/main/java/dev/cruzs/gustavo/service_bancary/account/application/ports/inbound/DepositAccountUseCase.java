package dev.cruzs.gustavo.service_bancary.account.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public interface DepositAccountUseCase {
  Account execute(DepositAccountCommand depositAccountCommand);
}
