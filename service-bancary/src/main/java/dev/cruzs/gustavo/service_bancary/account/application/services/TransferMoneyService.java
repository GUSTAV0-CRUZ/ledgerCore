package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.TransferMoneyUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.TransferMoneyCommand;
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

public class TransferMoneyService implements TransferMoneyUseCase {
  private final AccountRepository accountRepository;
  private final HistoryService historyService;
  private final AntiFraudService antiFraudService;
  private final MovementRepository movementRepository;

  public TransferMoneyService(
      AccountRepository accountRepository,
      HistoryService historyService,
      AntiFraudService antiFraudService, MovementRepository movementRepository
  ) {
    this.accountRepository = accountRepository;
    this.historyService = historyService;
    this.antiFraudService = antiFraudService;
    this.movementRepository = movementRepository;
  }

  @Override
  public Account execute(TransferMoneyCommand transferMoneyCommand) {
    var accountSender = accountRepository.findByUserIdOrException(transferMoneyCommand.senderUserId());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    String dateToIdempotencyKeyString = LocalDateTime.now(ZoneOffset.UTC).format(formatter);

    String idempotencyKey = accountSender.getId().toString() + dateToIdempotencyKeyString + "z";
    var movement = movementRepository.findByIdempotencyKey(idempotencyKey);

    if (movement.isPresent()) throw new IllegalArgumentException("Idempotency key already exists");

    this.antiFraudService.evaluate(
        new EvaluateCommand(
            accountSender.getId(),
            accountSender.getNumber().getNumber(),
            transferMoneyCommand.amount()
        )
    );

    var accountRecipient = accountRepository.findByNumberOrException(transferMoneyCommand.recipientNumberAccount());

    if (accountSender.getId().equals(accountRecipient.getId()))
      throw new IllegalArgumentException("You cannot transfer to yourself.");

    accountSender.withdraw(transferMoneyCommand.amount());
    accountRecipient.deposit(transferMoneyCommand.amount());

    this.accountRepository.updateBalance(accountSender.getId(), accountSender.getBalance());
    this.accountRepository.updateBalance(accountRecipient.getId(), accountRecipient.getBalance());

    this.movementRepository.save(
        Movement.create(idempotencyKey, MovementEnum.TRANSFER, accountSender.getId(), transferMoneyCommand.amount())
    );

    this.historyService.create(
        new CreateHistoryCommand(
            accountSender.getId(),
            transferMoneyCommand.amount(),
            accountRecipient.getNumber().getNumber(),
            Account.INSTITUTION
        )
    );

    return accountSender;
  }
}
