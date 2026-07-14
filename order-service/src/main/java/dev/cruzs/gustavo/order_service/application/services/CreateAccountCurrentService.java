package dev.cruzs.gustavo.order_service.application.services;

import dev.cruzs.gustavo.order_service.application.ports.in.CreateAccountCurrentUseCase;
import dev.cruzs.gustavo.order_service.application.ports.in.commands.CreateAccountCurrentCommand;
import dev.cruzs.gustavo.order_service.application.ports.out.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAccountCurrentService implements CreateAccountCurrentUseCase {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final AccountService accountService;

  public CreateAccountCurrentService(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public void execute(CreateAccountCurrentCommand createAccountCommand) {
    this.accountService.createCurrentAccount(
        createAccountCommand.userId()
    );

    this.logger.info("Try creating new account current with userId {}", createAccountCommand.userId());
  }
}
