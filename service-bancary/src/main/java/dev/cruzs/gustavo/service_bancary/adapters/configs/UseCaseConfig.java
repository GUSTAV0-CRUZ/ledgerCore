package dev.cruzs.gustavo.service_bancary.adapters.configs;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.CreateAccountUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.DepositAccountUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.TransferMoneyUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.application.services.CreateAccountService;
import dev.cruzs.gustavo.service_bancary.application.services.DepositAccountService;
import dev.cruzs.gustavo.service_bancary.application.services.TransferMoneyService;
import dev.cruzs.gustavo.service_bancary.application.services.WithdrawAccountService;
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
    return new TransferMoneyService(this.accountRepository);
  }
}
