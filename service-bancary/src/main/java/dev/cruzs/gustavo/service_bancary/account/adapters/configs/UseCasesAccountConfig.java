package dev.cruzs.gustavo.service_bancary.account.adapters.configs;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.decorators.TransactionalTransferMoneyDecorator;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.*;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.HistoryService;
import dev.cruzs.gustavo.service_bancary.account.application.services.*;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesAccountConfig {
  private final AccountRepository accountRepository;
  private final HistoryService historyService;

  public UseCasesAccountConfig(AccountRepository accountRepository, HistoryService historyService) {
    this.accountRepository = accountRepository;
    this.historyService = historyService;
  }

  @Bean
  public CreateAccountCurrentUseCase createAccountUseCase() {
    return new CreateAccountCurrentService(this.accountRepository);
  }

  @Bean
  public DepositAccountUseCase depositAccountUseCase() {
    return new DepositAccountService(this.accountRepository, this.historyService);
  }

  @Bean
  public WithdrawAccountUseCase withdrawAccountUseCase() {
    return new WithdrawAccountService(this.accountRepository, this.historyService);
  }

  @Bean
  public TransferMoneyUseCase transferMoneyUseCase() {
    TransferMoneyUseCase transferMoneyUseCase = new TransferMoneyService(this.accountRepository, this.historyService);

    return new TransactionalTransferMoneyDecorator(transferMoneyUseCase);
  }

  @Bean
  public FindAccountByUserIdUseCase findAccountByUserIdUseCase() {
    return new FindAccountByUserIdService(this.accountRepository);
  }
}
