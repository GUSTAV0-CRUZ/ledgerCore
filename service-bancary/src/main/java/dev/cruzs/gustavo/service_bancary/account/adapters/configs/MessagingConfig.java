package dev.cruzs.gustavo.service_bancary.account.adapters.configs;

import dev.cruzs.gustavo.service_bancary.account.adapters.inbound.messaging.AccountConsumer;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.CreateAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.DepositAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.TransferMoneyUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.WithdrawAccountUseCase;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.CreateAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.DepositAccountCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.TransferMoneyCommand;
import dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands.WithdrawAccountCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessagingConfig {
  @Bean
  public AccountConsumer accountConsumer(
      CreateAccountUseCase createAccountUseCase,
      DepositAccountUseCase depositAccountUseCase,
      WithdrawAccountUseCase withdrawAccountUseCase,
      TransferMoneyUseCase transferMoneyUseCase
  ) {
    return new AccountConsumer(
        createAccountUseCase,
        depositAccountUseCase,
        withdrawAccountUseCase,
        transferMoneyUseCase
    );
  }

  @Bean
  public Consumer<CreateAccountCommand> createAccountConsumer(AccountConsumer accountConsumer) {
    return accountConsumer.createAccountConsumer();
  }

  @Bean
  public Consumer<DepositAccountCommand> depositAccountConsumer(AccountConsumer accountConsumer) {
    return accountConsumer.depositAccountConsumer();
  }

  @Bean
  public Consumer<WithdrawAccountCommand> withdrawAccountConsumer(AccountConsumer accountConsumer) {
    return accountConsumer.withdrawAccountConsumer();
  }

  @Bean
  public Consumer<TransferMoneyCommand> transferMoneyAccountConsumer(AccountConsumer accountConsumer) {
    return accountConsumer.transferMoneyAccountConsumer();
  }
}
