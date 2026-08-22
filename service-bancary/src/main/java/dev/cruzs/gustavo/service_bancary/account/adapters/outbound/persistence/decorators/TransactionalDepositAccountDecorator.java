package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.decorators;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.DepositAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalDepositAccountDecorator implements DepositAccountUseCase {
  private final DepositAccountUseCase depositAccountUseCase;

  public TransactionalDepositAccountDecorator(DepositAccountUseCase depositAccountUseCase) {
    this.depositAccountUseCase = depositAccountUseCase;
  }

  @Transactional
  @Override
  public Account execute(DepositAccountCommand transferMoneyCommand) {
    return depositAccountUseCase.execute(transferMoneyCommand);
  }
}
