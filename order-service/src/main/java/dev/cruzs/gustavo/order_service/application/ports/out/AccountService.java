package dev.cruzs.gustavo.order_service.application.ports.out;

import java.util.UUID;

public interface AccountService {
  void createCurrentAccount(UUID userId);
}
