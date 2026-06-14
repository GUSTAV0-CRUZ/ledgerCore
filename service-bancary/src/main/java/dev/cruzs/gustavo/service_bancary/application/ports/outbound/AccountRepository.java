package dev.cruzs.gustavo.service_bancary.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.application.ports.outbound.exceptions.NotFoundException;
import dev.cruzs.gustavo.service_bancary.domain.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
  Account save(Account account);
  void saveAll(List<Account> accounts);
  Optional<Account> findById(UUID id);

  default Account findByIdOrException(UUID id) {
    return this.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
  }
}
