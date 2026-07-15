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
    if (command.sender().equals(command.recipient()))
      throw new IllegalArgumentException("You cannot transfer to yourself.");

    var accountSender = accountRepository.findByIdOrException(command.sender());
    var accountRecipient = accountRepository.findByIdOrException(command.recipient());

    accountSender.withdraw(command.amount());
    accountRecipient.deposit(command.amount());

    this.accountRepository.updateBalance(command.sender(), accountSender.getBalance());
    this.accountRepository.updateBalance(command.recipient(), accountRecipient.getBalance());

    return accountSender;
  }
}
