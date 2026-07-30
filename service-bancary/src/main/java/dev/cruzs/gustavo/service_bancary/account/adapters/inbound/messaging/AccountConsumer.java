package dev.cruzs.gustavo.service_bancary.account.adapters.inbound.messaging;

import dev.cruzs.gustavo.service_bancary.account.adapters.inbound.messaging.dtos.TransferMoneyRequestDto;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.CreateAccountCurrentUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.DepositAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.TransferMoneyUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.CreateAccountCurrentCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import dev.cruzs.gustavo.service_bancary.account.domain.valueObjects.NumberAccount;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountConsumer {
  private final Logger logger = LoggerFactory.getLogger(AccountConsumer.class);
  private final CreateAccountCurrentUseCase createAccountCurrentUseCase;
  private final DepositAccountUseCase depositAccountUseCase;
  private final WithdrawAccountUseCase withdrawAccountUseCase;
  private final TransferMoneyUseCase transferMoneyUseCase;

  public AccountConsumer(
      CreateAccountCurrentUseCase createAccountCurrentUseCase,
      DepositAccountUseCase depositAccountUseCase,
      WithdrawAccountUseCase withdrawAccountUseCase,
      TransferMoneyUseCase transferMoneyUseCase
  ) {
    this.createAccountCurrentUseCase = createAccountCurrentUseCase;
    this.depositAccountUseCase = depositAccountUseCase;
    this.withdrawAccountUseCase = withdrawAccountUseCase;
    this.transferMoneyUseCase = transferMoneyUseCase;
  }

  public void createAccountConsumer(CreateAccountCurrentCommand createAccountCurrentCommand) {
    Account account = this.createAccountCurrentUseCase.execute(createAccountCurrentCommand);
    logger.info("Account Id: {} created.", account.getId());
  }

  public void depositAccountConsumer(DepositAccountCommand depositAccountCommand) {
    Account account = this.depositAccountUseCase.execute(depositAccountCommand);
    logger.info("Account Id: ({}) deposit amount: {}", account.getId(), depositAccountCommand.amount());
  }

  public void withdrawAccountConsumer(WithdrawAccountCommand withdrawAccountCommand) {
    Account account = this.withdrawAccountUseCase.execute(withdrawAccountCommand);
    logger.info("Account Id: ({}) withdraw amount: {}", account.getId(), withdrawAccountCommand.amount());
  }

  @Transactional
  public void transferMoneyAccountConsumer(TransferMoneyRequestDto transferMoneyRequestDto) {
    Account account = this.transferMoneyUseCase.execute(
        new TransferMoneyCommand(
            transferMoneyRequestDto.senderUserId(),
            transferMoneyRequestDto.amount(),
            NumberAccount.restore(transferMoneyRequestDto.recipientNumberAccount())
        )
    );
    logger.info(
        "Account Id: ({}) transfer money with amount: {} for account Id: ({})",
        transferMoneyRequestDto.senderUserId(),
        transferMoneyRequestDto.amount(),
        transferMoneyRequestDto.recipientNumberAccount()
    );
  }
}
