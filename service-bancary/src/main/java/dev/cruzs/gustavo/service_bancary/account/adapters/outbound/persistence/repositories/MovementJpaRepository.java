package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.repositories;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.models.MovementModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovementJpaRepository extends JpaRepository<MovementModel, UUID> {
  Optional<MovementModel> findByIdempotencyKey(String idempotencyKey);
}
