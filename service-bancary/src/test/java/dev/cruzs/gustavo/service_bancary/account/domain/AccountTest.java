package dev.cruzs.gustavo.service_bancary.account.domain;

import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountStatusEnum;
import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
  private Account account;

  @BeforeEach
  void setUp() {
    UUID userId = UUID.randomUUID();
    Integer agency = 999;
    String number = "9999999-9";
    BigDecimal balance = BigDecimal.valueOf(9999.99);
    AccountTypeEnum typeAccount = AccountTypeEnum.CURRENT;

    this.account = Account.create(
        userId,
        agency,
        number,
        balance,
        typeAccount
    );
  }

  @Test
  @DisplayName("Should created one account with success")
  void createSuccess() {
    UUID userId = UUID.randomUUID();
    Integer agency = 999;
    String number = "9999999-9";
    BigDecimal balance = BigDecimal.valueOf(9999.99);
    AccountTypeEnum typeAccount = AccountTypeEnum.CURRENT;

    Account result = Account.create(
        userId,
        agency,
        number,
        balance,
        typeAccount
    );

    assertEquals(userId, result.getUserId());
    assertEquals(agency, result.getAgency());
    assertEquals(number, result.getNumber());
    assertEquals(balance, result.getBalance());
    assertEquals(typeAccount, result.getTypeAccount());
  }

  @Test
  @DisplayName("Should restored one account with success")
  void restoreSuccess() {
    var result = Account.restore(
        account.getId(),
        account.getUserId(),
        account.getAgency(),
        account.getNumber(),
        account.getBalance(),
        account.getTypeAccount(),
        account.getStatus()
    );

    assertEquals(account.getId(), result.getId());
    assertEquals(account.getUserId(), result.getUserId());
    assertEquals(account.getAgency(), result.getAgency());
    assertEquals(account.getNumber(), result.getNumber());
    assertEquals(account.getBalance(), result.getBalance());
    assertEquals(account.getTypeAccount(), result.getTypeAccount());
    assertEquals(account.getStatus(), result.getStatus());
  }

  @Test
  @DisplayName("Should return error: (UserId can't be null)")
  void checkUserIdErrorCase1() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Account.create(
            null,
            account.getAgency(),
            account.getNumber(),
            account.getBalance(),
            account.getTypeAccount()
        )
    );

    assertEquals("UserId can't be null", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (Agency can't be null)")
  void checkAgencyErrorCase1() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Account.create(
            account.getUserId(),
            null,
            account.getNumber(),
            account.getBalance(),
            account.getTypeAccount()
        )
    );

    assertEquals("Agency can't be null", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (Agency can't be negative)")
  void checkAgencyErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> Account.create(
            account.getUserId(),
            -1,
            account.getNumber(),
            account.getBalance(),
            account.getTypeAccount()
        )
    );

    assertEquals("Agency can't be negative", result.getMessage());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("Should return error: (Number can't be null or empty)")
  void checkNumberError(String invalidNumber) {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> Account.create(
            account.getUserId(),
            account.getAgency(),
            invalidNumber,
            account.getBalance(),
            account.getTypeAccount()
        )
    );

    assertEquals("Number can't be null or empty", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (Balance can't be null)")
  void checkBalanceErrorCase1() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> Account.create(
            account.getUserId(),
            account.getAgency(),
            account.getNumber(),
            null,
            account.getTypeAccount()
        )
    );

    assertEquals("Balance can't be null", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (Balance can't be negative)")
  void checkBalanceErrorCase2() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> Account.create(
            account.getUserId(),
            account.getAgency(),
            account.getNumber(),
            BigDecimal.valueOf(-1),
            account.getTypeAccount()
        )
    );

    assertEquals("Balance can't be negative", result.getMessage());
  }

  @Test
  @DisplayName("Should return error: (TypeAccount can't be null)")
  void checkTypeAccountError() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> Account.create(
            account.getUserId(),
            account.getAgency(),
            account.getNumber(),
            account.getBalance(),
            null
        )
    );

    assertEquals("TypeAccount can't be null", result.getMessage());
  }

  @Test
  @DisplayName("Should be successfully validated")
  void validateActiveSuccess() {
    account.changeAccountStatus(AccountStatusEnum.ACTIVE);
    assertDoesNotThrow(() -> account.validateActive());
  }

  @Test
  @DisplayName("Should return error: (Account must be ACTIVE)")
  void validateActiveError() {
    account.changeAccountStatus(AccountStatusEnum.BLOCKED);
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> account.validateActive()
    );

    assertEquals("Account must be ACTIVE", result.getMessage());
  }

  @Test
  @DisplayName("Should deposit successfully")
  void depositSuccess() {
    BigDecimal frozenBalance = account.getBalance();
    BigDecimal amount = BigDecimal.valueOf(8.99);

    account.deposit(amount);

    assertEquals(frozenBalance.add(amount), account.getBalance());
  }

  @Test
  @DisplayName("Should not depositing with amount negative in account, with error: (Amount can't be negative)")
  void depositError() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> account.deposit(BigDecimal.valueOf(-1))
    );

    assertEquals("Amount can't be negative", result.getMessage());
  }

  @Test
  @DisplayName("Should withdraw successfully")
  void withdrawSuccess() {
    BigDecimal frozenBalance = account.getBalance();
    BigDecimal amount = BigDecimal.valueOf(8.99);
    account.withdraw(amount);

    assertEquals(frozenBalance.subtract(amount), account.getBalance());
  }

  @Test
  @DisplayName("Should not withdraw with balance insufficient in account, with error: (Insufficient balance)")
  void withdrawError() {
    BigDecimal frozenBalance = account.getBalance();
    BigDecimal addition = BigDecimal.valueOf(1);
    BigDecimal amount = frozenBalance.add(addition);

    var result = assertThrows(
        IllegalArgumentException.class,
        () -> account.withdraw(amount)
    );

    assertEquals("Insufficient balance", result.getMessage());
  }
}