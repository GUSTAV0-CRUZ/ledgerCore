package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.account.domain.exceptions.NotFoundAccountException;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
  Account save(Account account);
  Optional<Account> findById(UUID id);
  Optional<Account> findByUserId(UUID userId);
  void updateBalance(UUID id, BigDecimal amount);

  default Account findByIdOrException(UUID id) {
    return this.findById(id).orElseThrow(() -> new NotFoundAccountException("Account not found"));
  }
}
