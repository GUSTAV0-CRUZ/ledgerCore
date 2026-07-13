package dev.cruzs.gustavo.order_service.application.ports.out;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
  void createCurrentAccount(
    UUID userId,
    BigDecimal balance
  );
}
