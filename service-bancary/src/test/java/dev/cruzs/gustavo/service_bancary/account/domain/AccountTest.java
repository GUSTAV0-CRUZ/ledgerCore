package dev.cruzs.gustavo.service_bancary.account.domain;

import dev.cruzs.gustavo.service_bancary.account.domain.Account;
import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountStatusEnum;
import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
  @DisplayName("Should updated the agency of account with success")
  void changeAgencySuccess() {
    account.changeAgency(888);

    assertEquals(888, account.getAgency());
  }

  @Test
  @DisplayName("Should not updated the agency of account with error: (Agency can't be negative)")
  void changeAgencyError() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> account.changeAgency(-1)
    );

    assertEquals("Agency can't be negative", result.getMessage());
  }

  @Test
  @DisplayName("Should updated the number of account with success")
  void changeNumberSuccess() {
    account.changeNumber("888888-8");

    assertEquals("888888-8", account.getNumber());
  }

  @Test
  @DisplayName("Should not updated the number of account with error: (Number can't be empty)")
  void changeNumberError() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> account.changeNumber("")
    );

    assertEquals("Number can't be empty", result.getMessage());
  }

  @Test
  @DisplayName("Should updated the typeAccount of account with success")
  void changeTypeAccountSuccess() {
    account.changeTypeAccount(AccountTypeEnum.SAVINGS);

    assertEquals(AccountTypeEnum.SAVINGS, account.getTypeAccount());
  }

  @Test
  @DisplayName("Should updated the accountStatus of account with success")
  void changeAccountStatusSuccess() {
    account.changeAccountStatus(AccountStatusEnum.BLOCKED);

    assertEquals(AccountStatusEnum.BLOCKED, account.getStatus());
  }

  @Test
  @DisplayName("Should be successfully validated")
  void validateActiveSuccess() {
    account.changeAccountStatus(AccountStatusEnum.ACTIVE);
    assertDoesNotThrow(() -> account.validateActive());
  }

  @Test
  @DisplayName("Should validated account with error: (Account status can't be ACTIVE)")
  void validateActiveError() {
    account.changeAccountStatus(AccountStatusEnum.BLOCKED);
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> account.validateActive()
    );

    assertEquals("Account status can't be ACTIVE", result.getMessage());
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