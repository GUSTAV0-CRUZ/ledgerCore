package dev.cruzs.gustavo.service_bancary.adapters.inbound.messaging;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.CreateAccountUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.DepositAccountUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.TransferMoneyUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.CreateAccountCommand;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.domain.Account;

import java.util.function.Consumer;

public class AccountConsumer {
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
      System.out.println("Account Id: " + account.getId() + " created.");
    };
  }

  public Consumer<DepositAccountCommand> depositAccountConsumer() {
    return depositAccountCommand -> {
      Account account = this.depositAccountUseCase.execute(depositAccountCommand);
      System.out.println(
          "Account Id: (" +
          account.getId() +
          ") deposit amount: " +
          depositAccountCommand.amount()
      );
    };
  }

  public Consumer<WithdrawAccountCommand> withdrawAccountConsumer() {
    return withdrawAccountCommand -> {
      Account account = this.withdrawAccountUseCase.execute(withdrawAccountCommand);
      System.out.println(
          "Account Id: (" +
          account.getId() +
          ") withdraw amount: " +
          withdrawAccountCommand.amount()
      );
    };
  }

  public Consumer<TransferMoneyCommand> transferMoneyAccountConsumer() {
    return transferMoneyCommand -> {
      Account account = this.transferMoneyUseCase.execute(transferMoneyCommand);
      System.out.println(
          "Account Id: (" +
          transferMoneyCommand.sender() +
          ") transfer money with amount: " +
          transferMoneyCommand.amount() +
          " for account Id: (" +
          transferMoneyCommand.recipient() +
          ")"
      );
    };
  }
}
