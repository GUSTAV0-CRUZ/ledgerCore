package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.HistoryService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.CreateHistoryCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public class WithdrawAccountService implements WithdrawAccountUseCase {
  private final AccountRepository accountRepository;
  private final HistoryService historyService;

  public WithdrawAccountService(AccountRepository accountRepository, HistoryService historyService) {
    this.accountRepository = accountRepository;
    this.historyService = historyService;
  }

  @Override
  public Account execute(WithdrawAccountCommand withdrawAccountCommand) {
    var account = accountRepository.findByUserIdOrException(withdrawAccountCommand.userId());

    account.withdraw(withdrawAccountCommand.amount());
    this.accountRepository.updateBalance(
        account.getId(),
        account.getBalance()
    );

    this.historyService.create(
        new CreateHistoryCommand(
            account.getId(),
            withdrawAccountCommand.amount(),
            "Withdraw",
            Account.INSTITUTION
        )
    );

    return account;
  }
}
