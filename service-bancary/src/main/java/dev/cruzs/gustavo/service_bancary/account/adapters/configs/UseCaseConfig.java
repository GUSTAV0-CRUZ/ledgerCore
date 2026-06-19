package dev.cruzs.gustavo.service_bancary.account.adapters.configs;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.decorators.TransactionalTransferMoneyDecorator;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.*;
import dev.cruzs.gustavo.service_bancary.account.application.services.*;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
  private final AccountRepository accountRepository;

  public UseCaseConfig(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Bean
  public CreateAccountUseCase createAccountUseCase() {
    return new CreateAccountService(this.accountRepository);
  }

  @Bean
  public DepositAccountUseCase depositAccountUseCase() {
    return new DepositAccountService(this.accountRepository);
  }

  @Bean
  public WithdrawAccountUseCase withdrawAccountUseCase() {
    return new WithdrawAccountService(this.accountRepository);
  }

  @Bean
  public TransferMoneyUseCase transferMoneyUseCase() {
    TransferMoneyUseCase transferMoneyUseCase = new TransferMoneyService(this.accountRepository);

    return new TransactionalTransferMoneyDecorator(transferMoneyUseCase);
  }

  @Bean
  public FindAccountByUserIdUseCase findAccountByUserIdUseCase() {
    return new FindAccountByUserIdService(this.accountRepository);
  }
}
