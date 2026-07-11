package dev.cruzs.gustavo.service_bancary.account.adapters.inbound.messaging;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.CreateAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.DepositAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.TransferMoneyUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.CreateAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class AccountConsumer {
  private final Logger logger = LoggerFactory.getLogger(AccountConsumer.class);
  private final CreateAccountUseCase createAccountUseCase;
  private final DepositAccountUseCase depositAccountUseCase;
  private final WithdrawAccountUseCase withdrawAccountUseCase;
  private final TransferMoneyUseCase transferMoneyUseCase;

  public AccountConsumer(
      CreateAccountUseCase createAccountUseCase,
      DepositAccountUseCase depositAccountUseCase,
      WithdrawAccountUseCase withdrawAccountUseCase,
      TransferMoneyUseCase transferMoneyUseCase
  ) {
    this.createAccountUseCase = createAccountUseCase;
    this.depositAccountUseCase = depositAccountUseCase;
    this.withdrawAccountUseCase = withdrawAccountUseCase;
    this.transferMoneyUseCase = transferMoneyUseCase;
  }

  public Consumer<CreateAccountCommand> createAccountConsumer() {
    return createAccountCommand -> {
      Account account = this.createAccountUseCase.execute(createAccountCommand);
      logger.info("Account Id: {} created.", account.getId());
    };
  }

  public Consumer<DepositAccountCommand> depositAccountConsumer() {
    return depositAccountCommand -> {
      Account account = this.depositAccountUseCase.execute(depositAccountCommand);
      logger.info("Account Id: ({}) deposit amount: {}", account.getId(), depositAccountCommand.amount());
    };
  }

  public Consumer<WithdrawAccountCommand> withdrawAccountConsumer() {
    return withdrawAccountCommand -> {
      Account account = this.withdrawAccountUseCase.execute(withdrawAccountCommand);
      logger.info("Account Id: ({}) withdraw amount: {}", account.getId(), withdrawAccountCommand.amount());
    };
  }

  @Transactional
  public Consumer<TransferMoneyCommand> transferMoneyAccountConsumer() {
    return transferMoneyCommand -> {
      Account account = this.transferMoneyUseCase.execute(transferMoneyCommand);
      logger.info(
          "Account Id: ({}) transfer money with amount: {} for account Id: ({})",
          transferMoneyCommand.sender(),
          transferMoneyCommand.amount(),
          transferMoneyCommand.recipient()
      );
    };
  }
}
