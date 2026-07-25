package dev.cruzs.gustavo.gateway_service.filters.messages.dtos;

import java.math.BigDecimal;

public record WithdrawAccountRequestDto(BigDecimal amount) {
}
