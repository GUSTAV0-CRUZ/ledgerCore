package dev.cruzs.gustavo.service_bancary.account.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public interface TransferMoneyUseCase {
  Account execute(TransferMoneyCommand command);
}
