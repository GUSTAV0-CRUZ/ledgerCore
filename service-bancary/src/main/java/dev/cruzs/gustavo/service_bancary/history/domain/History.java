package dev.cruzs.gustavo.service_bancary.history.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class History {
  private final UUID id;
  private UUID accountId;
  private BigDecimal amount;
  private String destinataryName;
  private String institutionName;
  private LocalDateTime transferDate;

  private History(
      UUID id,
      UUID accountId,
      BigDecimal amount,
      String destinataryName,
      String institutionName,
      LocalDateTime transferDate
  ) {
    this.id = id;
    this.checkAccountId(accountId);
    this.checkAmount(amount);
    this.checkDestinataryName(destinataryName);
    this.checkInstitutionName(institutionName);
    this.checkTransferDate(transferDate);
  }

  public static History create(
      UUID accountId,
      BigDecimal amount,
      String destinataryName,
      String institutionName,
      LocalDateTime transferDate
  ) {
    return new History(
        UUID.randomUUID(),
        accountId,
        amount,
        destinataryName,
        institutionName,
        transferDate
    );
  }

  public static History restore(
      UUID id,
      UUID accountId,
      BigDecimal amount,
      String destinataryName,
      String institutionName,
      LocalDateTime transferDate
  ) {
    return new History(
        id,
        accountId,
        amount,
        destinataryName,
        institutionName,
        transferDate
    );
  }

  private void checkAccountId(UUID accountId) {
    if (accountId == null) throw new IllegalArgumentException("AccountId must not be null");

    this.accountId = accountId;
  }

  private void checkAmount(BigDecimal amount) {
    if (amount == null) throw new IllegalArgumentException("Amount must not be null");

    this.amount = amount;
  }

  private void checkDestinataryName(String destinataryName) {
    if (destinataryName == null || destinataryName.isEmpty())
      throw new IllegalArgumentException("DestinataryName must not be null or empty");

    if (destinataryName.length() < 3)
      throw new IllegalArgumentException("DestinataryName must have at least 3 characters");

    this.destinataryName = destinataryName;
  }

  private void checkInstitutionName(String institutionName) {
    if (institutionName == null || institutionName.isEmpty())
      throw new IllegalArgumentException("InstitutionName must not be null or empty");

    if (institutionName.length() < 3)
      throw new IllegalArgumentException("InstitutionName must have at least 3 characters");

    this.institutionName = institutionName;
  }

  private void checkTransferDate(LocalDateTime transferDate) {
    if (transferDate == null) throw new IllegalArgumentException("TransferDate must not be null");

    this.transferDate = transferDate;
  }

  public UUID getId() {
    return id;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getDestinataryName() {
    return destinataryName;
  }

  public String getInstitutionName() {
    return institutionName;
  }

  public LocalDateTime getTransferDate() {
    return transferDate;
  }
}
