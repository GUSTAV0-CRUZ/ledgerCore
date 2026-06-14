package dev.cruzs.gustavo.service_bancary.adapters.outbound.persistence;

import dev.cruzs.gustavo.service_bancary.adapters.outbound.persistence.maps.AccountMap;
import dev.cruzs.gustavo.service_bancary.adapters.outbound.persistence.models.AccountModel;
import dev.cruzs.gustavo.service_bancary.adapters.outbound.persistence.repositories.AccountJpaRepository;
import dev.cruzs.gustavo.service_bancary.application.ports.outbound.AccountRepository;
import dev.cruzs.gustavo.service_bancary.domain.Account;

import java.util.List;
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
  public void saveAll(List<Account> accounts) {
    List<AccountModel> accountModels = accounts.stream().map(AccountMap::mapToAccountModel).toList();

    this.accountJpaRepository.saveAll(accountModels);
  }

  @Override
  public Optional<Account> findById(UUID id) {
    AccountModel accountModel = this.accountJpaRepository.findById(id).orElse(null);

    if (accountModel == null) return Optional.empty();

    return Optional.of(AccountMap.mapToAccount(accountModel));
  }
}
