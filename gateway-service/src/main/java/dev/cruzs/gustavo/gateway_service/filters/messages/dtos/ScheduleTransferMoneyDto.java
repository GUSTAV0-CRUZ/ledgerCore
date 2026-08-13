package dev.cruzs.gustavo.gateway_service.filters.messages.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ScheduleTransferMoneyDto(
    UUID senderUserId,
    BigDecimal amount,
    String recipientNumberAccount,
    Instant scheduledDate
) {
}
