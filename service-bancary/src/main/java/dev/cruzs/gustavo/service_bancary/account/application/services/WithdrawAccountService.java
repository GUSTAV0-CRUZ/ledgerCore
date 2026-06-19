package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public class WithdrawAccountService implements WithdrawAccountUseCase {
  private final AccountRepository accountRepository;

  public WithdrawAccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account execute(WithdrawAccountCommand command) {
    var account = accountRepository.findByIdOrException(command.id());

    account.withdraw(command.amount());
    this.accountRepository.updateBalance(
        command.id(),
        account.getBalance()
    );

    return account;
  }
}
