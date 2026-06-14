package dev.cruzs.gustavo.service_bancary.application.services;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.domain.Account;

public class WithdrawAccountService implements WithdrawAccountUseCase {
  private final AccountRepository accountRepository;

  public WithdrawAccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account execute(WithdrawAccountCommand command) {
    var account = accountRepository.findById(command.id());

    account.withdraw(command.amount());

    return accountRepository.save(account);
  }
}
