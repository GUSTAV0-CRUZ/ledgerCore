package dev.cruzs.gustavo.service_bancary.schedulingWork.domain;

import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.enums.SchedulingEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SchedulingTest {
  private Scheduling scheduling;

  @BeforeEach
  void setUp() {
    scheduling = Scheduling.create(
        UUID.randomUUID(),
        new BigDecimal("99.99"),
        Instant.now().plusSeconds(1000),
        "Number-123"
    );
  }

  @DisplayName("Should create Scheduling with success")
  @Test
  void create() {
    UUID senderUserId = UUID.randomUUID();
    BigDecimal amount = new BigDecimal("99.99");
    Instant scheduledDate = Instant.now().plusSeconds(1000);
    String recipientNumberAccount = "Number-123";

    Scheduling result = Scheduling.create(senderUserId, amount, scheduledDate, recipientNumberAccount);

    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals(senderUserId, result.getSenderUserId());
    assertEquals(amount, result.getAmount());
    assertEquals(recipientNumberAccount, result.getRecipientNumberAccount());
    assertEquals(scheduledDate, result.getScheduledDate());
    assertEquals(SchedulingEnum.PENDING, result.getStatus());
  }

  @DisplayName("Should restore Scheduling with success")
  @Test
  void restore() {
    Scheduling result = Scheduling.restore(
        scheduling.getId(),
        scheduling.getSenderUserId(),
        scheduling.getAmount(),
        scheduling.getRecipientNumberAccount(),
        scheduling.getScheduledDate(),
        scheduling.getStatus()
    );

    assertNotNull(result);
    assertNotNull(scheduling.getId());
    assertEquals(scheduling.getSenderUserId(), result.getSenderUserId());
    assertEquals(scheduling.getAmount(), result.getAmount());
    assertEquals(scheduling.getRecipientNumberAccount(), result.getRecipientNumberAccount());
    assertEquals(scheduling.getScheduledDate(), result.getScheduledDate());
    assertEquals(scheduling.getStatus(), result.getStatus());
  }


  @DisplayName("Should return error: (id must not be null)")
  @Test
  void checkIdWithError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Scheduling.restore(
            null,
            scheduling.getSenderUserId(),
            scheduling.getAmount(),
            scheduling.getRecipientNumberAccount(),
            scheduling.getScheduledDate(),
            scheduling.getStatus()
        )
    );

    assertEquals("id must not be null", result.getMessage());
  }

  @DisplayName("Should return error: (senderUserId must not be null)")
  @Test
  void checkSenderUserIdWithError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Scheduling.create(
            null,
            scheduling.getAmount(),
            scheduling.getScheduledDate(),
            scheduling.getRecipientNumberAccount()
        )
    );

    assertEquals("senderUserId must not be null", result.getMessage());
  }

  @DisplayName("Should return error: (amount must not be null)")
  @Test
  void checkAmountWithErrorCase1() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> Scheduling.create(
            scheduling.getSenderUserId(),
            null,
            scheduling.getScheduledDate(),
            scheduling.getRecipientNumberAccount()
        )
    );

    assertEquals("amount must not be null", result.getMessage());
  }

  @DisplayName("Should return error: (amount must not be negative)")
  @Test
  void checkAmountWithErrorCase2() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> Scheduling.create(
            scheduling.getSenderUserId(),
            new BigDecimal(-1),
            scheduling.getScheduledDate(),
            scheduling.getRecipientNumberAccount()
        )
    );

    assertEquals("amount must not be negative", result.getMessage());
  }

  @DisplayName("Should return error: (recipientNumberAccount must not be null or empty")
  @ParameterizedTest
  @NullAndEmptySource
  void checkRecipientNumberAccountWithError(String recipientNumberAccount) {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> Scheduling.create(
            scheduling.getSenderUserId(),
            scheduling.getAmount(),
            scheduling.getScheduledDate(),
            recipientNumberAccount
        )
    );

    assertEquals("recipientNumberAccount must not be null or empty", result.getMessage());
  }

  @DisplayName("Should return error: (scheduledDate must not be null)")
  @Test
  void checkScheduledDateWithErrorCase1() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> Scheduling.create(
            scheduling.getSenderUserId(),
            scheduling.getAmount(),
            null,
            scheduling.getRecipientNumberAccount()
        )
    );

    assertEquals("scheduledDate must not be null", result.getMessage());
  }

  @DisplayName("Should return error: (scheduledDate must not be before now)")
  @Test
  void checkScheduledDateWithErrorCase2() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> Scheduling.create(
            scheduling.getSenderUserId(),
            scheduling.getAmount(),
            Instant.now().minusSeconds(1),
            scheduling.getRecipientNumberAccount()
        )
    );

    assertEquals("scheduledDate must not be before now", result.getMessage());
  }

  @DisplayName("Should return error: (schedulingStatus must not be null)")
  @Test
  void checkSchedulingTypeWithError() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> Scheduling.restore(
            scheduling.getId(),
            scheduling.getSenderUserId(),
            scheduling.getAmount(),
            scheduling.getRecipientNumberAccount(),
            scheduling.getScheduledDate(),
            null
        )
    );

    assertEquals("schedulingStatus must not be null", result.getMessage());
  }

  @DisplayName("Should update status with success")
  @Test
  void updateSchedulingStatusWithSuccess() {
    scheduling.updateSchedulingStatus(SchedulingEnum.PROCESSED);

    assertEquals(SchedulingEnum.PROCESSED, scheduling.getStatus());
  }

  @DisplayName("Should return error: (schedulingStatus must not be null)")
  @Test
  void updateSchedulingStatusWithError() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> scheduling.updateSchedulingStatus(null)
    );

    assertEquals("schedulingStatus must not be null", result.getMessage());
  }

  @DisplayName("Should update scheduledDate with success")
  @Test
  void updateScheduledDateWithSuccess() {
    Instant scheduledDate =  Instant.now().plusSeconds(1);
    scheduling.updateScheduledDate(scheduledDate);

    assertEquals(scheduledDate, scheduling.getScheduledDate());
  }

  @DisplayName("Should return error: (scheduling must be PENDING to update scheduledDate)")
  @Test
  void updateScheduledDateWithError() {
    scheduling.updateSchedulingStatus(SchedulingEnum.PROCESSED);

    var result = assertThrows(
        IllegalArgumentException.class,
        () -> scheduling.updateScheduledDate(Instant.now().plusSeconds(1))
    );

    assertEquals("scheduling must be PENDING to update scheduledDate", result.getMessage());
  }
}
