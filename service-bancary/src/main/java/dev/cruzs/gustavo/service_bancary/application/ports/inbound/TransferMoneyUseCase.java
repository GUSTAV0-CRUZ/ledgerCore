package dev.cruzs.gustavo.service_bancary.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.service_bancary.domain.Account;

public interface TransferMoneyUseCase {
  Account execute(TransferMoneyCommand command);
}
