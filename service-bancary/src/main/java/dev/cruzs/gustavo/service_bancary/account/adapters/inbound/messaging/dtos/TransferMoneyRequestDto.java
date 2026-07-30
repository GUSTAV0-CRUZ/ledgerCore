package dev.cruzs.gustavo.service_bancary.account.adapters.inbound.messaging.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferMoneyRequestDto(UUID senderUserId, BigDecimal amount, String recipientNumberAccount) {
}
