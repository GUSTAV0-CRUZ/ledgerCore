package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.WithdrawAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AntiFraudService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.HistoryService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.MovementRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.CreateHistoryCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.EvaluateCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import dev.cruzs.gustavo.service_bancary.account.domain.Movement;
import dev.cruzs.gustavo.service_bancary.account.domain.enums.MovementEnum;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class WithdrawAccountService implements WithdrawAccountUseCase {
  private final AccountRepository accountRepository;
  private final HistoryService historyService;
  private final AntiFraudService antiFraudService;
  private final MovementRepository movementRepository;

  public WithdrawAccountService(
      AccountRepository accountRepository,
      HistoryService historyService,
      AntiFraudService antiFraudService,
      MovementRepository movementRepository
  ) {
    this.accountRepository = accountRepository;
    this.historyService = historyService;
    this.antiFraudService = antiFraudService;
    this.movementRepository = movementRepository;
  }

  @Override
  public Account execute(WithdrawAccountCommand withdrawAccountCommand) {
    var account = accountRepository.findByUserIdOrException(withdrawAccountCommand.userId());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    String dateToIdempotencyKeyString = LocalDateTime.now(ZoneOffset.UTC).format(formatter);

    String idempotencyKey = account.getId().toString() + dateToIdempotencyKeyString + "z";
    var movement = movementRepository.findByIdempotencyKey(idempotencyKey);

    if (movement.isPresent()) throw new IllegalArgumentException("Idempotency key already exists");

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

    this.movementRepository.save(
        Movement.create(idempotencyKey, MovementEnum.WITHDRAW, account.getId(), withdrawAccountCommand.amount())
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
