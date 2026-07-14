package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.CreateAccountCurrentUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.CreateAccountCurrentCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountTypeEnum;

import java.math.BigDecimal;

public class CreateAccountCurrentService implements CreateAccountCurrentUseCase {
  private final AccountRepository accountRepository;

  public CreateAccountCurrentService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account execute(CreateAccountCurrentCommand createAccountCurrentCommand) {
    var account = Account.create(
        createAccountCurrentCommand.userId(),
        BigDecimal.valueOf(10.00),
        AccountTypeEnum.CURRENT
    );

    return accountRepository.save(account);
  }
}
