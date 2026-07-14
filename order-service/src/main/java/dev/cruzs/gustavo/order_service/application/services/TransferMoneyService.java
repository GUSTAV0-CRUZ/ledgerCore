package dev.cruzs.gustavo.order_service.application.services;

import dev.cruzs.gustavo.order_service.application.ports.in.TransferMoneyUseCase;
import dev.cruzs.gustavo.order_service.application.ports.in.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.order_service.application.ports.out.AccountService;

public class TransferMoneyService implements TransferMoneyUseCase {
  private final AccountService accountService;

  public TransferMoneyService(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public void execute(TransferMoneyCommand transferMoneyCommand) {
    this.accountService.transferMoney(
        transferMoneyCommand.sender(),
        transferMoneyCommand.amount(),
        transferMoneyCommand.recipient()
    );
  }
}
