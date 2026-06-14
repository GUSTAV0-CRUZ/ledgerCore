package dev.cruzs.gustavo.service_bancary.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.domain.Account;

public interface DepositAccountUseCase {
  Account execute(DepositAccountCommand depositAccountCommand);
}
