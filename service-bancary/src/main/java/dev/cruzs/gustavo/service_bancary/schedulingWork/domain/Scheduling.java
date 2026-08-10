package dev.cruzs.gustavo.service_bancary.schedulingWork.domain;

import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.enums.SchedulingEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Scheduling {
  private UUID id;
  private UUID senderUserId;
  private BigDecimal amount;
  private String recipientNumberAccount;
  private LocalDateTime scheduledDate;
  private SchedulingEnum status;

  private Scheduling(
      UUID id,
      UUID senderUserId,
      BigDecimal amount,
      String recipientNumberAccount,
      LocalDateTime scheduledDate,
      SchedulingEnum schedulingStatus
  ) {
    this.checkId(id);
    this.checkSenderUserId(senderUserId);
    this.checkAmount(amount);
    this.checkRecipientNumberAccount(recipientNumberAccount);
    this.scheduledDate = scheduledDate;
    this.checkSchedulingType(schedulingStatus);
  }

  public static Scheduling create(
      UUID senderUserId,
      BigDecimal amount,
      LocalDateTime scheduledDate,
      String recipientNumberAccount
  ) {
    return new Scheduling(
        UUID.randomUUID(),
        senderUserId,
        amount,
        recipientNumberAccount,
        checkScheduledDate(scheduledDate),
        SchedulingEnum.PENDING
    );
  }

  public static Scheduling restore(
      UUID id,
      UUID senderUserId,
      BigDecimal amount,
      String recipientNumberAccount,
      LocalDateTime scheduledDate,
      SchedulingEnum schedulingStatus
  ) {
    if (scheduledDate == null) throw new NullPointerException("scheduledDate is null");
    return new Scheduling(id, senderUserId, amount, recipientNumberAccount, scheduledDate,  schedulingStatus);
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

  private static LocalDateTime checkScheduledDate(LocalDateTime scheduledDate) {
    if (scheduledDate == null)
      throw new IllegalArgumentException("scheduledDate must not be null");

    if (scheduledDate.isBefore(LocalDateTime.now()))
      throw new IllegalArgumentException("scheduledDate must not be before now");

    return scheduledDate;
  }

  private void checkSchedulingType(SchedulingEnum schedulingStatus) {
    if (schedulingStatus == null)
      throw new IllegalArgumentException("schedulingStatus must not be null");

    this.status = schedulingStatus;
  }

  public void updateScheduledDate(LocalDateTime scheduledDate) {
    if (status != SchedulingEnum.PENDING)
      throw new IllegalArgumentException("scheduling must be PENDING to update scheduledDate");
    this.scheduledDate = checkScheduledDate(scheduledDate);
  }

  public void updateSchedulingType(SchedulingEnum schedulingStatus) {
    this.checkSchedulingType(schedulingStatus);
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

  public SchedulingEnum getStatus() {
    return status;
  }
}
