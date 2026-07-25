package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.DepositAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public class DepositAccountService implements DepositAccountUseCase {
  private final AccountRepository accountRepository;

  public DepositAccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account execute(DepositAccountCommand depositAccountCommand) {
    var account = accountRepository.findByUserIdOrException(depositAccountCommand.userId());

    account.deposit(depositAccountCommand.amount());

    this.accountRepository.updateBalance(
        account.getId(),
        account.getBalance()
    );

    return account;
  }
}
