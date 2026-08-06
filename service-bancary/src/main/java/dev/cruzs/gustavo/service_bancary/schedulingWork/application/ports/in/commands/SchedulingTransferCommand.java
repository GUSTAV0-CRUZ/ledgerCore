package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands;

import dev.cruzs.gustavo.service_bancary.account.domain.valueObjects.NumberAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SchedulingTransferCommand(
    UUID senderUserId,
    BigDecimal amount,
    NumberAccount recipientNumberAccount,
    LocalDateTime scheduledDate
) {
}
