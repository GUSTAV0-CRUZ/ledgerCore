package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateHistoryCommand(
    UUID accountId,
    BigDecimal amount,
    String destinataryName,
    String institutionName
) {
}
