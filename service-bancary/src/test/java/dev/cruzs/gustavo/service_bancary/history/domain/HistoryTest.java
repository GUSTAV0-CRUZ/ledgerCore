package dev.cruzs.gustavo.service_bancary.history.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HistoryTest {
  private static History history;

  @Test
  @DisplayName("Should create history with success")
  void createHistorySuccess() {
    UUID accountId = UUID.randomUUID();
    BigDecimal amount = BigDecimal.valueOf(99.99);
    String destinataryName = "Gustavo Cruz";
    String institutionName = "ledger core - Institution";
    LocalDateTime transferDate = LocalDateTime.now();

    History result = History.create(
        accountId,
        amount,
        destinataryName,
        institutionName
    );

    assertNotNull(result.getId());
    assertEquals(accountId, result.getAccountId());
    assertEquals(amount, result.getAmount());
    assertEquals(destinataryName, result.getDestinataryName());
    assertEquals(institutionName, result.getInstitutionName());
  }

  @BeforeAll
  static void setUp() {
    history = History.create(
      UUID.randomUUID(),
      BigDecimal.valueOf(99.99),
      "Gustavo Cruz",
      "ledger core - Institution"
    );
  }

  @Test
  @DisplayName("Should return error: (AccountId must not be null)")
  void checkAccountIdError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> History.create(
            null,
            history.getAmount(),
            history.getDestinataryName(),
            history.getInstitutionName()
        )
    );

    assertEquals("AccountId must not be null", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (Amount must not be null)")
  void checkAmountError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> History.create(
            history.getAccountId(),
            null,
            history.getDestinataryName(),
            history.getInstitutionName()
        )
    );

    assertEquals("Amount must not be null", result.getMessage());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("Should return error: (DestinataryName must not be null or empty)")
  void checkDestinataryNameErrorCase1(String destinataryName) {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> History.create(
            history.getAccountId(),
            history.getAmount(),
            destinataryName,
            history.getInstitutionName()
        )
    );

    assertEquals("DestinataryName must not be null or empty", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (DestinataryName must have at least 3 characters)")
  void checkDestinataryNameErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> History.create(
            history.getAccountId(),
            history.getAmount(),
            "gu",
            history.getInstitutionName()
        )
    );

    assertEquals("DestinataryName must have at least 3 characters", result.getMessage());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("Should return error: (InstitutionName must not be null or empty)")
  void checkInstitutionNameErrorCase1(String institutionName) {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> History.create(
            history.getAccountId(),
            history.getAmount(),
            history.getDestinataryName(),
            institutionName
        )
    );

    assertEquals("InstitutionName must not be null or empty", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (InstitutionName must have at least 3 characters)")
  void checkInstitutionNameErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> History.create(
            history.getAccountId(),
            history.getAmount(),
            history.getDestinataryName(),
            "lc"
        )
    );

    assertEquals("InstitutionName must have at least 3 characters", result.getMessage());
  }
}