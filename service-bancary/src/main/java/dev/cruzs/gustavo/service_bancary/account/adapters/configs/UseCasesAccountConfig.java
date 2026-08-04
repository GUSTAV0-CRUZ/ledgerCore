package dev.cruzs.gustavo.service_bancary.account.adapters.configs;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.decorators.TransactionalTransferMoneyDecorator;
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

  public UseCasesAccountConfig(
      AccountRepository accountRepository,
      HistoryService historyService,
      AntiFraudService antiFraudService
  ) {
    this.accountRepository = accountRepository;
    this.historyService = historyService;
    this.antiFraudService = antiFraudService;
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
    return new DepositAccountService(this.accountRepository, this.historyService);
  }

  @Bean
  public WithdrawAccountUseCase withdrawAccountUseCase() {
    return new WithdrawAccountService(this.accountRepository, this.historyService, this.antiFraudService);
  }

  @Bean
  public TransferMoneyUseCase transferMoneyUseCase() {
    TransferMoneyUseCase transferMoneyUseCase = new TransferMoneyService(
        this.accountRepository,
        this.historyService,
        this.antiFraudService
    );

    return new TransactionalTransferMoneyDecorator(transferMoneyUseCase);
  }

  @Bean
  public FindAccountByUserIdUseCase findAccountByUserIdUseCase() {
    return new FindAccountByUserIdService(this.accountRepository);
  }
}
