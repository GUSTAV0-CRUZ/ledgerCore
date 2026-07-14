package dev.cruzs.gustavo.order_service.application.services;

import dev.cruzs.gustavo.order_service.application.ports.in.TransferMoneyUseCase;
import dev.cruzs.gustavo.order_service.application.ports.in.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.order_service.application.ports.out.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferMoneyService implements TransferMoneyUseCase {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
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

    this.logger.info(
        "Try transfer {} of account {} to account {}",
        transferMoneyCommand.amount(),
        transferMoneyCommand.sender(),
        transferMoneyCommand.recipient()
    );
  }
}
