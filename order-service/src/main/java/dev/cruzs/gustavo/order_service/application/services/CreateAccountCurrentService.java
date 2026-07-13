package dev.cruzs.gustavo.order_service.application.services;

import dev.cruzs.gustavo.order_service.application.ports.in.CreateAccountCurrentUseCase;
import dev.cruzs.gustavo.order_service.application.ports.in.commands.CreateAccountCurrentCommand;
import dev.cruzs.gustavo.order_service.application.ports.out.AccountService;

public class CreateAccountCurrentService implements CreateAccountCurrentUseCase {
  private final AccountService accountService;

  public CreateAccountCurrentService(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public void execute(CreateAccountCurrentCommand createAccountCommand) {
    this.accountService.createCurrentAccount(
        createAccountCommand.userId(),
        createAccountCommand.balance()
    );
  }
}
