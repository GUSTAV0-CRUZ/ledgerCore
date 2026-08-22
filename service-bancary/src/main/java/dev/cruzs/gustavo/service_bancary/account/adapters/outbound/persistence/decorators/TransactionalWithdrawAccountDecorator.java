package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.decorators;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalWithdrawAccountDecorator implements WithdrawAccountUseCase {
  private final WithdrawAccountUseCase withdrawAccountUseCase;

  public TransactionalWithdrawAccountDecorator(WithdrawAccountUseCase withdrawAccountUseCase) {
    this.withdrawAccountUseCase = withdrawAccountUseCase;
  }

  @Transactional
  @Override
  public Account execute(WithdrawAccountCommand transferMoneyCommand) {
    return withdrawAccountUseCase.execute(transferMoneyCommand);
  }
}
