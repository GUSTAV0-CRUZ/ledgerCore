package dev.cruzs.gustavo.service_bancary.account.domain;

import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountStatusEnum;
import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountTypeEnum;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
  private final UUID id;
  private final UUID userId;
  public static final Integer BANK_CODE = 999;
  public static final String INSTITUTION = "LedgerCore Bank - institution";
  private Integer agency;
  private String number;
  private BigDecimal balance;
  private AccountTypeEnum typeAccount;
  private AccountStatusEnum status;

  private Account(
      UUID id,
      UUID userId,
      Integer agency,
      String number,
      BigDecimal balance,
      AccountTypeEnum typeAccount,
      AccountStatusEnum status
  ) {
    this.id = id;
    this.userId = userId;
    this.changeAgency(agency);
    this.changeNumber(number);
    this.changeBalance(balance);
    this.changeTypeAccount(typeAccount);
    this.changeAccountStatus(status);
  }

  public static Account create(
      UUID userId,
      Integer agency,
      String number,
      BigDecimal balance,
      AccountTypeEnum typeAccount
  ) {
    return new Account(
        UUID.randomUUID(),
        userId,
        agency,
        number,
        balance,
        typeAccount,
        AccountStatusEnum.ACTIVE
    );
  }

  public static Account restore(
      UUID id,
      UUID userId,
      Integer agency,
      String number,
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

  public void changeAgency(Integer newAgency) {
    if (newAgency < 0) throw new IllegalArgumentException("Agency can't be negative");

    this.agency = newAgency;
  }

  public void changeNumber(String newNumber) {
    if (newNumber.isEmpty()) throw new IllegalArgumentException("Number can't be empty");

    this.number = newNumber;
  }

  private void changeBalance(BigDecimal balance) {
    if (balance.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Balance can't be negative");

    this.balance = balance;
  }

   public void changeTypeAccount(AccountTypeEnum newType) {
    this.typeAccount = newType;
   }

  public void changeAccountStatus(AccountStatusEnum newStatus) {
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

  public String getNumber() {
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
      throw new IllegalArgumentException("Account status can't be ACTIVE");
  }

  private void validateAmount(BigDecimal amount) {
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
