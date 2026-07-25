package dev.cruzs.gustavo.gateway_service.filters.messages.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawAccountDto(UUID userId, BigDecimal amount) {
}
