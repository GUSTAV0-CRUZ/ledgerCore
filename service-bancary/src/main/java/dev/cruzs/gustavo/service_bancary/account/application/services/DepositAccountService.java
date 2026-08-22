package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.DepositAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.HistoryService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.MovementRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.CreateHistoryCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import dev.cruzs.gustavo.service_bancary.account.domain.Movement;
import dev.cruzs.gustavo.service_bancary.account.domain.enums.MovementEnum;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DepositAccountService implements DepositAccountUseCase {
  private final AccountRepository accountRepository;
  private final HistoryService historyService;
  private final MovementRepository movementRepository;

  public DepositAccountService(
      AccountRepository accountRepository,
      HistoryService historyService,
      MovementRepository movementRepository
  ) {
    this.accountRepository = accountRepository;
    this.historyService = historyService;
    this.movementRepository = movementRepository;
  }

  @Override
  public Account execute(DepositAccountCommand depositAccountCommand) {
    var account = accountRepository.findByUserIdOrException(depositAccountCommand.userId());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    String dateToIdempotencyKeyString = LocalDateTime.now(ZoneOffset.UTC).format(formatter);

    String idempotencyKey = account.getId().toString() + dateToIdempotencyKeyString + "z";
    var movement = movementRepository.findByIdempotencyKey(idempotencyKey);

    if (movement.isPresent()) throw new IllegalArgumentException("Idempotency key already exists");

    account.deposit(depositAccountCommand.amount());

    this.accountRepository.updateBalance(
        account.getId(),
        account.getBalance()
    );

    this.movementRepository.save(
        Movement.create(idempotencyKey, MovementEnum.DEPOSIT, account.getId(), depositAccountCommand.amount())
    );

    this.historyService.create(
        new CreateHistoryCommand(
            account.getId(),
            depositAccountCommand.amount(),
            "Deposit",
            Account.INSTITUTION
        )
    );

    return account;
  }
}
