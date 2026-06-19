package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.maps.AccountMap;
import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.models.AccountModel;
import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.repositories.AccountJpaRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class AccountRepositoryAdapter implements AccountRepository {
  private final AccountJpaRepository accountJpaRepository;

  public AccountRepositoryAdapter(AccountJpaRepository accountJpaRepository) {
    this.accountJpaRepository = accountJpaRepository;
  }

  @Override
  public Account save(Account account) {
    AccountModel accountModel = AccountMap.mapToAccountModel(account);

    this.accountJpaRepository.save(accountModel);

    return AccountMap.mapToAccount(accountModel);
  }

  @Override
  public Optional<Account> findById(UUID id) {
    AccountModel accountModel = this.accountJpaRepository.findById(id).orElse(null);

    if (accountModel == null) return Optional.empty();

    return Optional.of(AccountMap.mapToAccount(accountModel));
  }

  @Override
  public Optional<Account> findByUserId(UUID userId) {
    AccountModel accountModel = this.accountJpaRepository.findByUserId(userId).orElse(null);

    if (accountModel == null) return Optional.empty();

    return Optional.of(AccountMap.mapToAccount(accountModel));
  }

  @Transactional
  @Override
  public void updateBalance(UUID id, BigDecimal amount) {
    int row = this.accountJpaRepository.updateBalance(id, amount);

    if (row == 0) throw new IllegalArgumentException("Account not updated balance");
  }
}
