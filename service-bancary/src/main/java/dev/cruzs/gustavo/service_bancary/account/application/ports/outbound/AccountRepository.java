package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.account.domain.exceptions.NotFoundAccountException;
import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import dev.cruzs.gustavo.service_bancary.account.domain.valueObjects.NumberAccount;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
  Account save(Account account);
  Optional<Account> findById(UUID id);
  Optional<Account> findByUserId(UUID userId);
  Optional<Account> findByNumber(NumberAccount number);
  void updateBalance(UUID id, BigDecimal amount);

  default Account findByIdOrException(UUID id) {
    return this.findById(id).orElseThrow(() -> new NotFoundAccountException("Account not found"));
  }

  default Account findByUserIdOrException(UUID id) {
    return this.findByUserId(id).orElseThrow(() -> new NotFoundAccountException("Account not found"));
  }

  default Account findByNumberOrException(NumberAccount number) {
    return this.findByNumber(number).orElseThrow(() -> new NotFoundAccountException("Account not found"));
  }
}
