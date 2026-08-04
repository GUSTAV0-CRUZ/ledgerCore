package dev.cruzs.gustavo.service_bancary.account.application.services;

import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.CreateAccountCurrentUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.CreateAccountCurrentCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.NotificationService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.UserService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.UserResponseDto;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountTypeEnum;

import java.math.BigDecimal;

public class CreateAccountCurrentService implements CreateAccountCurrentUseCase {
  private final AccountRepository accountRepository;
  private final NotificationService notificationService;
  private final UserService userService;

  public CreateAccountCurrentService(
      AccountRepository accountRepository,
      NotificationService notificationService,
      UserService userService
  ) {
    this.accountRepository = accountRepository;
    this.notificationService = notificationService;
    this.userService = userService;
  }

  @Override
  public Account execute(CreateAccountCurrentCommand createAccountCurrentCommand) {
    UserResponseDto userResponseDto = userService.findById(createAccountCurrentCommand.userId());

    var account = Account.create(
        userResponseDto.id(),
        BigDecimal.valueOf(10.00),
        AccountTypeEnum.CURRENT
    );

    Account accountCreated = accountRepository.save(account);
    notificationService.sendEmail(
        userResponseDto.email(),
        "Account created in " + Account.INSTITUTION,
        "Hi " + userResponseDto.name() + ", account created with success. Welcome the " + Account.INSTITUTION
    );

    return accountCreated;
  }
}
