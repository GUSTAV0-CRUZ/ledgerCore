package dev.cruzs.gustavo.service_bancary.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.domain.Account;

import java.util.List;
import java.util.UUID;

public interface AccountRepository {
  Account save(Account account);
  void saveAll(List<Account> accounts);
  Account findById(UUID id);
}
