package dev.cruzs.gustavo.gateway_service.filters.messages.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ScheduleTransferMoneyRequestDto(
    String recipientNumberAccount,
    BigDecimal amount,
    LocalDateTime scheduledDate
) {
}
