package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.TransferMoneyUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.HistoryService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.CreateHistoryCommand;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public class TransferMoneyService implements TransferMoneyUseCase {
  private final AccountRepository accountRepository;
  private final HistoryService historyService;

  public TransferMoneyService(AccountRepository accountRepository, HistoryService historyService) {
    this.accountRepository = accountRepository;
    this.historyService = historyService;
  }

  @Override
  public Account execute(TransferMoneyCommand transferMoneyCommand) {
    var accountSender = accountRepository.findByUserIdOrException(transferMoneyCommand.senderUserId());
    var accountRecipient = accountRepository.findByNumberOrException(transferMoneyCommand.recipientNumberAccount());

    if (accountSender.getId().equals(accountRecipient.getId()))
      throw new IllegalArgumentException("You cannot transfer to yourself.");

    accountSender.withdraw(transferMoneyCommand.amount());
    accountRecipient.deposit(transferMoneyCommand.amount());

    this.accountRepository.updateBalance(accountSender.getId(), accountSender.getBalance());
    this.accountRepository.updateBalance(accountRecipient.getId(), accountRecipient.getBalance());

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
