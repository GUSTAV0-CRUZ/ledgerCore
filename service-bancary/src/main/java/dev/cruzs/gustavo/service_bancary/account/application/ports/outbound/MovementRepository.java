package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.account.domain.Movement;

import java.util.Optional;
import java.util.UUID;

public interface MovementRepository {
  void save(Movement movement);
  Optional<Movement> findById(UUID id);
  Optional<Movement> findByIdempotencyKey(String idempotencyKey);
}
