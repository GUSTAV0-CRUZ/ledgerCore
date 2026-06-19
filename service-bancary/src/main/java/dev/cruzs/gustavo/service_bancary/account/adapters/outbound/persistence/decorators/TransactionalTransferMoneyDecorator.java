package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.decorators;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.TransferMoneyUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalTransferMoneyDecorator implements TransferMoneyUseCase {
  private final TransferMoneyUseCase transferMoneyUseCase;

  public TransactionalTransferMoneyDecorator(TransferMoneyUseCase transferMoneyUseCase) {
    this.transferMoneyUseCase = transferMoneyUseCase;
  }

  @Transactional
  @Override
  public Account execute(TransferMoneyCommand command) {
    return transferMoneyUseCase.execute(command);
  }
}
