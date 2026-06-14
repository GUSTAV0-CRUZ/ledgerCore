package dev.cruzs.gustavo.service_bancary.application.services;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.TransferMoneyUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.service_bancary.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.domain.Account;

import java.util.Arrays;

public class TransferMoneyService implements TransferMoneyUseCase {
  private final AccountRepository accountRepository;

  public TransferMoneyService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account execute(TransferMoneyCommand command) {
    var accountSender = accountRepository.findByIdOrException(command.sender());
    var accountRecipient = accountRepository.findByIdOrException(command.recipient());

    accountSender.withdraw(command.amount());
    accountRecipient.deposit(command.amount());

    accountRepository.saveAll(Arrays.asList(accountSender, accountRecipient));

    return accountSender;
  }
}
