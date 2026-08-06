package dev.cruzs.gustavo.service_bancary.schedulingWork.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Scheduling {
  private UUID id;
  private UUID senderUserId;
  private BigDecimal amount;
  private String recipientNumberAccount;
  private LocalDateTime scheduledDate;

  private Scheduling(
      UUID id,
      UUID senderUserId,
      BigDecimal amount,
      String recipientNumberAccount,
      LocalDateTime scheduledDate
  ) {
    this.checkId(id);
    this.checkSenderUserId(senderUserId);
    this.checkAmount(amount);
    this.checkRecipientNumberAccount(recipientNumberAccount);
    this.checkScheduledDate(scheduledDate);
  }

  public static Scheduling create(
      UUID senderUserId,
      BigDecimal amount,
      String recipientNumberAccount
  ) {
    return new Scheduling(UUID.randomUUID(), senderUserId, amount, recipientNumberAccount, LocalDateTime.now());
  }

  public static Scheduling restore(
      UUID id,
      UUID senderUserId,
      BigDecimal amount,
      String recipientNumberAccount,
      LocalDateTime scheduledDate
  ) {
    return new Scheduling(id, senderUserId, amount, recipientNumberAccount, scheduledDate);
  }

  private void checkId(UUID id) {
    if (id == null)
      throw new IllegalArgumentException("id must not be null");

    this.id = id;
  }

  private void checkSenderUserId(UUID senderUserId) {
    if (senderUserId == null)
      throw new IllegalArgumentException("senderUserId must not be null");

    this.senderUserId = senderUserId;
  }

  private void checkAmount(BigDecimal amount) {
    if (amount == null)
      throw new IllegalArgumentException("amount must not be null");

    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("amount must not be negative");

    this.amount = amount;
  }

  private void checkRecipientNumberAccount(String recipientNumberAccount) {
    if (recipientNumberAccount == null ||  recipientNumberAccount.isEmpty())
      throw new IllegalArgumentException("recipientNumberAccount must not be null or empty");

    this.recipientNumberAccount = recipientNumberAccount;
  }

  private void checkScheduledDate(LocalDateTime scheduledDate) {
    if (scheduledDate == null)
      throw new IllegalArgumentException("scheduledDate must not be null");

    if (scheduledDate.isBefore(LocalDateTime.now()))
      throw new IllegalArgumentException("scheduledDate must not be before now");

    this.scheduledDate = scheduledDate;
  }

  public void updateScheduledDate(LocalDateTime scheduledDate) {
    this.checkScheduledDate(scheduledDate);
  }

  public UUID getId() {
    return id;
  }

  public UUID getSenderUserId() {
    return senderUserId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getRecipientNumberAccount() {
    return recipientNumberAccount;
  }

  public LocalDateTime getScheduledDate() {
    return scheduledDate;
  }
}
