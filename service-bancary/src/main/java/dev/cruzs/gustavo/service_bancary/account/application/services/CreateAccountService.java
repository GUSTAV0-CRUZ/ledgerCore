package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.CreateAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.CreateAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public class CreateAccountService implements CreateAccountUseCase {
  private final AccountRepository accountRepository;

  public CreateAccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account execute(CreateAccountCommand createAccountCommand) {
    var account = Account.create(
        createAccountCommand.userId(),
        createAccountCommand.agency(),
        createAccountCommand.number(),
        createAccountCommand.balance(),
        createAccountCommand.typeAccount()
    );

    return accountRepository.save(account);
  }
}
