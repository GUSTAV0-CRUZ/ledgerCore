package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.TransferMoneyUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

public class TransferMoneyService implements TransferMoneyUseCase {
  private final AccountRepository accountRepository;

  public TransferMoneyService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account execute(TransferMoneyCommand command) {
    var accountSender = accountRepository.findByUserIdOrException(command.senderUserId());
    var accountRecipient = accountRepository.findByNumberOrException(command.recipientNumberAccount());

    if (accountSender.getId().equals(accountRecipient.getId()))
      throw new IllegalArgumentException("You cannot transfer to yourself.");

    accountSender.withdraw(command.amount());
    accountRecipient.deposit(command.amount());

    this.accountRepository.updateBalance(accountSender.getId(), accountSender.getBalance());
    this.accountRepository.updateBalance(accountRecipient.getId(), accountRecipient.getBalance());

    return accountSender;
  }
}
