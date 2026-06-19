package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.FindAccountByUserIdUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.FindAccountByUserIdCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.exceptions.NotFoundException;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public class FindAccountByUserIdService implements FindAccountByUserIdUseCase {
  private final AccountRepository accountRepository;

  public FindAccountByUserIdService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account execute(FindAccountByUserIdCommand command) {
    return this.accountRepository.findByUserId(command.userId()).orElseThrow(
        () -> new NotFoundException("Account not found")
    );
  }
}
