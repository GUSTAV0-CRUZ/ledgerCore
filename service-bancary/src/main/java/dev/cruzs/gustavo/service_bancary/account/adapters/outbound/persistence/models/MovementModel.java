package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.models;

import dev.cruzs.gustavo.service_bancary.account.domain.enums.MovementEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Table(name = "tb_movement")
public class MovementModel {
  @Id
  private UUID id;

  @Column(name = "idempotency_key", nullable = false, unique = true)
  private String idempotencyKey;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MovementEnum type;

  @Column(name = "account_id", nullable = false)
  private UUID accountId;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;
}
