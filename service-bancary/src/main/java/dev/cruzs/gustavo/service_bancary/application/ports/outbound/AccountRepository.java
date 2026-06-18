package dev.cruzs.gustavo.service_bancary.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.application.ports.outbound.exceptions.NotFoundException;
import dev.cruzs.gustavo.service_bancary.domain.Account;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
  Account save(Account account);
  Optional<Account> findById(UUID id);
  Optional<Account> findByUserId(UUID userId);
  void updateBalance(UUID id, BigDecimal amount);

  default Account findByIdOrException(UUID id) {
    return this.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
  }
}
