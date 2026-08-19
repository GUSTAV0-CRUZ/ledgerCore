package dev.cruzs.gustavo.service_bancary.account.domain;

import dev.cruzs.gustavo.service_bancary.account.domain.enums.MovementEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {
  private Movement movement;

  @BeforeEach
  void setUp() {
    String idempotencyKey = "idempotencyKey123";
    MovementEnum type = MovementEnum.TRANSFER;
    UUID accountId =  UUID.randomUUID();
    BigDecimal amount =  new BigDecimal("99.99");
    movement = Movement.create(idempotencyKey, type, accountId, amount);
  }

  @Test
  void createWithSuccess() {
    String idempotencyKey = "idempotencyKey123";
    MovementEnum type = MovementEnum.TRANSFER;
    UUID accountId =  UUID.randomUUID();
    BigDecimal amount =  new BigDecimal("99.99");

    Movement result = Movement.create(idempotencyKey, type, accountId, amount);

    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals(idempotencyKey, result.getIdempotencyKey());
    assertEquals(type, result.getType());
    assertEquals(accountId, result.getAccountId());
    assertEquals(amount, result.getAmount());
    assertNotNull(result.getCreatedAt());
  }

  @Test
  void restoreWithSuccess() {
    var result = Movement.restore(
        movement.getId(),
        movement.getIdempotencyKey(),
        movement.getType(),
        movement.getAccountId(),
        movement.getAmount(),
        movement.getCreatedAt()
    );

    assertNotNull(result);
    assertEquals(movement.getId(), result.getId());
    assertEquals(movement.getIdempotencyKey(), result.getIdempotencyKey());
    assertEquals(movement.getType(), result.getType());
    assertEquals(movement.getAccountId(), result.getAccountId());
    assertEquals(movement.getAmount(), result.getAmount());
    assertEquals(movement.getCreatedAt(), result.getCreatedAt());
  }

  @Test
  @DisplayName("Should return error: (id cannot be null)")
  void checkIdWithError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Movement.restore(
            null,
            movement.getIdempotencyKey(),
            movement.getType(),
            movement.getAccountId(),
            movement.getAmount(),
            movement.getCreatedAt()
        )
    );

    assertEquals("id cannot be null", result.getMessage());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("Should return error: (idempotencyKey cannot be null or empty)")
  void checkIdempotencyKeyWithError(String idempotencyKeyInvalid) {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Movement.create(
            idempotencyKeyInvalid,
            movement.getType(),
            movement.getAccountId(),
            movement.getAmount()
        )
    );

    assertEquals("idempotencyKey cannot be null or empty", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (type cannot be null)")
  void checkTypeWithError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Movement.create(
            movement.getIdempotencyKey(),
            null,
            movement.getAccountId(),
            movement.getAmount()
        )
    );

    assertEquals("type cannot be null", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (accountId cannot be null)")
  void checkAccountIdWithError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Movement.create(
            movement.getIdempotencyKey(),
            movement.getType(),
            null,
            movement.getAmount()
        )
    );

    assertEquals("accountId cannot be null", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (amount cannot be null)")
  void checkAmountWithError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Movement.create(
            movement.getIdempotencyKey(),
            movement.getType(),
            movement.getAccountId(),
            null
        )
    );

    assertEquals("amount cannot be null", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (createdAt must not be null)")
  void validateCreatedAtWithErrorCase1() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Movement.restore(
            movement.getId(),
            movement.getIdempotencyKey(),
            movement.getType(),
            movement.getAccountId(),
            movement.getAmount(),
            null
        )
    );

    assertEquals("createdAt must not be null", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (createdAt can't be after now)")
  void validateCreatedAtWithErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Movement.restore(
            movement.getId(),
            movement.getIdempotencyKey(),
            movement.getType(),
            movement.getAccountId(),
            movement.getAmount(),
            Instant.now().plusSeconds(100)
        )
    );

    assertEquals("createdAt can't be after now", result.getMessage());
  }
}
