package dev.cruzs.gustavo.service_bancary.account.adapters.configs;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.AccountRepositoryAdapter;
import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.repositories.AccountJpaRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {
  @Bean
  public AccountRepository accountRepositoryAdapter(AccountJpaRepository accountJpaRepository) {
    return new AccountRepositoryAdapter(accountJpaRepository);
  }
}
