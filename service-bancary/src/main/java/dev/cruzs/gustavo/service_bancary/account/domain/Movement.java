package dev.cruzs.gustavo.service_bancary.account.domain;

import dev.cruzs.gustavo.service_bancary.account.domain.enums.MovementEnum;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Movement {
private final UUID id;
private final String idempotencyKey;
private final MovementEnum type;
private final UUID accountId;
private final BigDecimal amount;
private final Instant createdAt;

  private Movement(
      UUID id,
      String idempotencyKey,
      MovementEnum type,
      UUID accountId,
      BigDecimal amount,
      Instant createdAt
  ) {
    this.id = checkId(id);
    this.idempotencyKey = checkIdempotencyKey(idempotencyKey);
    this.type = checkType(type);
    this.accountId = checkAccountId(accountId);
    this.amount = checkAmount(amount);
    this.createdAt = checkCreatedAt(createdAt);
  }

  public static Movement create(String idempotencyKey, MovementEnum type, UUID accountId, BigDecimal amount) {
    return new Movement(
        UUID.randomUUID(),
        idempotencyKey,
        type,
        accountId,
        amount,
        Instant.now()
    );
  }

  public static Movement restore(
      UUID id,
      String idempotencyKey,
      MovementEnum type,
      UUID accountId,
      BigDecimal amount,
      Instant createdAt
  ) {
    return new Movement(
        id,
        idempotencyKey,
        type,
        accountId,
        amount,
        validateCreatedAt(createdAt)
    );
  }

  private UUID checkId(UUID id) {
    if (id == null) throw new IllegalArgumentException("id cannot be null");
    return id;
  }

  private String checkIdempotencyKey(String idempotencyKey) {
    if (idempotencyKey == null || idempotencyKey.isEmpty())
      throw new IllegalArgumentException("idempotencyKey cannot be null or empty");

    return idempotencyKey;
  }

  private MovementEnum checkType(MovementEnum type) {
    if (type == null) throw new IllegalArgumentException("type cannot be null");

    return type;
  }

  private UUID checkAccountId(UUID accountId) {
    if (accountId == null) throw new IllegalArgumentException("accountId cannot be null");

    return accountId;
  }

  private BigDecimal checkAmount(BigDecimal amount) {
    if (amount == null) throw new IllegalArgumentException("amount cannot be null");

    return amount;
  }

  private Instant checkCreatedAt(Instant createdAt) {
    if (createdAt == null) throw new IllegalArgumentException("createdAt cannot be null");

    return createdAt;
  }

  private static Instant validateCreatedAt(Instant createdAt) {
    if (createdAt == null) throw new IllegalArgumentException("createdAt must not be null");

    if (createdAt.isAfter(Instant.now()))
      throw new IllegalArgumentException("createdAt can't be after now");

    return createdAt;
  }

  public UUID getId() {
    return id;
  }

  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public MovementEnum getType() {
    return type;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
