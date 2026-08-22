package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.maps.MovementMap;
import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.models.MovementModel;
import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.repositories.MovementJpaRepository;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.MovementRepository;
import dev.cruzs.gustavo.service_bancary.account.domain.Movement;

import java.util.Optional;
import java.util.UUID;

public class MovementRepositoryAdapter implements MovementRepository {
  private final MovementJpaRepository movementJpaRepository;

  public MovementRepositoryAdapter(MovementJpaRepository movementJpaRepository) {
    this.movementJpaRepository = movementJpaRepository;
  }

  @Override
  public void save(Movement movement) {
    MovementModel movementModel = MovementMap.mapToMovementModel(movement);
    movementJpaRepository.save(movementModel);
  }

  @Override
  public Optional<Movement> findById(UUID id) {
    return movementJpaRepository.findById(id).map(MovementMap::mapToMovement);
  }

  @Override
  public Optional<Movement> findByIdempotencyKey(String idempotencyKey) {
    return movementJpaRepository.findByIdempotencyKey(idempotencyKey).map(MovementMap::mapToMovement);
  }
}
