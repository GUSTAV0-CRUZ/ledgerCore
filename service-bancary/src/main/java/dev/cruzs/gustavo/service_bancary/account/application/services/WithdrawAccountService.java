package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AntiFraudService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.HistoryService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.CreateHistoryCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.EvaluateCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public class WithdrawAccountService implements WithdrawAccountUseCase {
  private final AccountRepository accountRepository;
  private final HistoryService historyService;
  private final AntiFraudService antiFraudService;

  public WithdrawAccountService(AccountRepository accountRepository, HistoryService historyService, AntiFraudService antiFraudService) {
    this.accountRepository = accountRepository;
    this.historyService = historyService;
    this.antiFraudService = antiFraudService;
  }

  @Override
  public Account execute(WithdrawAccountCommand withdrawAccountCommand) {
    var account = accountRepository.findByUserIdOrException(withdrawAccountCommand.userId());

    this.antiFraudService.evaluate(
        new EvaluateCommand(
          account.getId(),
          account.getNumber().getNumber(),
          withdrawAccountCommand.amount()
        )
    );

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
