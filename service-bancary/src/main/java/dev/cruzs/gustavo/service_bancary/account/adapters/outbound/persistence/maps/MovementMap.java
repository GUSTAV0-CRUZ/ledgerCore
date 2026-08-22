package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.maps;

import dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.models.MovementModel;
import dev.cruzs.gustavo.service_bancary.account.domain.Movement;

public class MovementMap {
  public static Movement mapToMovement(MovementModel movementModel) {
    return Movement.restore(
        movementModel.getId(),
        movementModel.getIdempotencyKey(),
        movementModel.getType(),
        movementModel.getAccountId(),
        movementModel.getAmount(),
        movementModel.getCreatedAt()
    );
  }

  public static MovementModel mapToMovementModel(Movement movement) {
    return MovementModel.builder()
        .id(movement.getId())
        .idempotencyKey(movement.getIdempotencyKey())
        .type(movement.getType())
        .accountId(movement.getAccountId())
        .amount(movement.getAmount())
        .createdAt(movement.getCreatedAt())
        .build();
  }
}
