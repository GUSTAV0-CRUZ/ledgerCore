package dev.cruzs.gustavo.service_bancary.account.adapters.configs;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.decorators.TransactionalDepositAccountDecorator;
import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.decorators.TransactionalTransferMoneyDecorator;
import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.decorators.TransactionalWithdrawAccountDecorator;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.*;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.*;
import dev.cruzs.gustavo.service_bancary.account.application.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesAccountConfig {
  private final AccountRepository accountRepository;
  private final HistoryService historyService;
  private final AntiFraudService antiFraudService;
  private final MovementRepository movementRepository;

  public UseCasesAccountConfig(
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

  @Bean
  public CreateAccountCurrentUseCase createAccountUseCase(
      NotificationService notificationService,
      UserService userService
  ) {
    return new CreateAccountCurrentService(this.accountRepository, notificationService, userService);
  }

  @Bean
  public DepositAccountUseCase depositAccountUseCase() {
    DepositAccountUseCase depositAccountUseCase = new DepositAccountService(this.accountRepository, this.historyService, this.movementRepository);

    return new TransactionalDepositAccountDecorator(depositAccountUseCase);
  }

  @Bean
  public WithdrawAccountUseCase withdrawAccountUseCase() {
    WithdrawAccountUseCase withdrawAccountUseCase = new WithdrawAccountService(
        this.accountRepository,
        this.historyService,
        this.antiFraudService,
        this.movementRepository
    );

    return new TransactionalWithdrawAccountDecorator(withdrawAccountUseCase);
  }

  @Bean
  public TransferMoneyUseCase transferMoneyUseCase() {
    TransferMoneyUseCase transferMoneyUseCase = new TransferMoneyService(
        this.accountRepository,
        this.historyService,
        this.antiFraudService,
        this.movementRepository
    );

    return new TransactionalTransferMoneyDecorator(transferMoneyUseCase);
  }

  @Bean
  public FindAccountByUserIdUseCase findAccountByUserIdUseCase() {
    return new FindAccountByUserIdService(this.accountRepository);
  }
}
