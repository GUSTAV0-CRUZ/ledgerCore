package dev.cruzs.gustavo.service_bancary.history.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HistoryTest {
  private static History history;

  @Test
  @DisplayName("Should create history with success")
  void createHistorySuccess() {
    UUID accountId = UUID.randomUUID();
    String destinataryName = "Gustavo Cruz";
    String institutionName = "ledger core - Institution";
    LocalDateTime transferDate = LocalDateTime.now();

    History result = History.create(
        accountId,
        destinataryName,
        institutionName,
        transferDate
    );

    assertNotNull(result.getId());
    assertEquals(accountId, result.getAccountId());
    assertEquals(destinataryName, result.getDestinataryName());
    assertEquals(institutionName, result.getInstitutionName());
    assertEquals(transferDate, result.getTransferDate());
  }

  @BeforeAll
  static void setUp() {
    history = History.create(
      UUID.randomUUID(),
      "Gustavo Cruz",
      "ledger core - Institution",
      LocalDateTime.now()
    );
  }

  @Test
  @DisplayName("Should return error: (AccountId must not be null)")
  void checkAccountIdError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> History.create(
            null,
            history.getDestinataryName(),
            history.getInstitutionName(),
            history.getTransferDate()
        )
    );

    assertEquals("AccountId must not be null", result.getMessage());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("Should return error: (DestinataryName must not be null or empty)")
  void checkDestinataryNameErrorCase1(String destinataryName) {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> History.create(
            history.getAccountId(),
            destinataryName,
            history.getInstitutionName(),
            history.getTransferDate()
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
            "gu",
            history.getInstitutionName(),
            history.getTransferDate()
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
            history.getDestinataryName(),
            institutionName,
            history.getTransferDate()
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
            history.getDestinataryName(),
            "lc",
            history.getTransferDate()
        )
    );

    assertEquals("InstitutionName must have at least 3 characters", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (TransferDate must not be null)")
  void checkTransferDateError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> History.create(
            history.getAccountId(),
            history.getDestinataryName(),
            history.getInstitutionName(),
            null
        )
    );

    assertEquals("TransferDate must not be null", result.getMessage());
  }
}