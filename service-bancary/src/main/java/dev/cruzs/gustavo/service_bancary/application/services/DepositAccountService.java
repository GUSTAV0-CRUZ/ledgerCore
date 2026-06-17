package dev.cruzs.gustavo.service_bancary.application.services;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.DepositAccountUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.domain.Account;

public class DepositAccountService implements DepositAccountUseCase {
  private final AccountRepository accountRepository;

  public DepositAccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account execute(DepositAccountCommand depositAccountCommand) {
    var account = accountRepository.findByIdOrException(depositAccountCommand.id());

    account.deposit(depositAccountCommand.amount());

    this.accountRepository.updateBalance(
        depositAccountCommand.id(),
        account.getBalance()
    );

    return account;
  }
}
