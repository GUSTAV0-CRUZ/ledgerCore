package dev.cruzs.gustavo.gateway_service.filters.messages.dtos;

import java.math.BigDecimal;
import java.time.Instant;

public record ScheduleTransferMoneyRequestDto(
    String recipientNumberAccount,
    BigDecimal amount,
    Instant scheduledDate
) {
}
