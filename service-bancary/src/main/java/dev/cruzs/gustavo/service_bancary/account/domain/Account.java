package dev.cruzs.gustavo.service_bancary.account.domain;

import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountStatusEnum;
import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountTypeEnum;
import dev.cruzs.gustavo.service_bancary.account.domain.valueObjects.NumberAccount;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
  private final UUID id;
  private UUID userId;
  public static final Integer BANK_CODE = 999;
  public static final String INSTITUTION = "LedgerCore Bank - institution";
  private Integer agency;
  private NumberAccount number;
  private BigDecimal balance;
  private AccountTypeEnum typeAccount;
  private AccountStatusEnum status;

  private Account(
      UUID id,
      UUID userId,
      Integer agency,
      NumberAccount number,
      BigDecimal balance,
      AccountTypeEnum typeAccount,
      AccountStatusEnum status
  ) {
    this.id = id;
    this.checkUserId(userId);
    this.checkAgency(agency);
    this.number = number;
    this.checkBalance(balance);
    this.checkTypeAccount(typeAccount);
    this.changeAccountStatus(status);
  }

  public static Account create(
      UUID userId,
      Integer agency,
      BigDecimal balance,
      AccountTypeEnum typeAccount
  ) {
    UUID id =  UUID.randomUUID();
    return new Account(
        id,
        userId,
        agency,
        NumberAccount.create(id),
        balance,
        typeAccount,
        AccountStatusEnum.ACTIVE
    );
  }

  public static Account restore(
      UUID id,
      UUID userId,
      Integer agency,
      NumberAccount number,
      BigDecimal balance,
      AccountTypeEnum typeAccount,
      AccountStatusEnum status
  ) {
    return new Account(
        id,
        userId,
        agency,
        number,
        balance,
        typeAccount,
        status
    );
  }

  private void checkUserId(UUID userId) {
    if (userId == null) throw new IllegalArgumentException("UserId can't be null");
    this.userId = userId;
  }

  private void checkAgency(Integer newAgency) {
    if (newAgency == null) throw new IllegalArgumentException("Agency can't be null");
    if (newAgency < 0) throw new IllegalArgumentException("Agency can't be negative");

    this.agency = newAgency;
  }

  private void checkBalance(BigDecimal balance) {
    if (balance == null) throw new IllegalArgumentException("Balance can't be null");
    if (balance.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Balance can't be negative");

    this.balance = balance;
  }

   private void checkTypeAccount(AccountTypeEnum newType) {
     if (newType == null) throw new IllegalArgumentException("TypeAccount can't be null");
    this.typeAccount = newType;
   }

  public void changeAccountStatus(AccountStatusEnum newStatus) {
    if (newStatus == null) throw new IllegalArgumentException("StatusAccount can't be null");
    this.status = newStatus;
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public Integer getAgency() {
    return agency;
  }

  public NumberAccount getNumber() {
    return number;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public AccountTypeEnum getTypeAccount() {
    return typeAccount;
  }

  public AccountStatusEnum getStatus() {
    return status;
  }


  public void validateActive() {
    if (!this.status.equals(AccountStatusEnum.ACTIVE))
      throw new IllegalArgumentException("Account must be ACTIVE");
  }

  private void validateAmount(BigDecimal amount) {
    if (amount == null) throw new IllegalArgumentException("Amount can't be null");
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Amount can't be negative");
  }

  public void deposit(BigDecimal amount) {
    this.validateActive();
    this.validateAmount(amount);
    this.balance = this.balance.add(amount);
  }

  public void withdraw(BigDecimal amount) {
    this.validateActive();
    this.validateAmount(amount);

    if (this.balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Insufficient balance");

    this.balance = this.balance.subtract(amount);
  }
}
