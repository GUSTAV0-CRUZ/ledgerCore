package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.DepositAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.HistoryService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.CreateHistoryCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public class DepositAccountService implements DepositAccountUseCase {
  private final AccountRepository accountRepository;
  private final HistoryService historyService;

  public DepositAccountService(AccountRepository accountRepository, HistoryService historyService) {
    this.accountRepository = accountRepository;
    this.historyService = historyService;
  }

  @Override
  public Account execute(DepositAccountCommand depositAccountCommand) {
    var account = accountRepository.findByUserIdOrException(depositAccountCommand.userId());

    account.deposit(depositAccountCommand.amount());

    this.accountRepository.updateBalance(
        account.getId(),
        account.getBalance()
    );

    this.historyService.create(
        new CreateHistoryCommand(
            account.getId(),
            depositAccountCommand.amount(),
            "Deposit",
            Account.INSTITUTION
        )
    );

    return account;
  }
}
