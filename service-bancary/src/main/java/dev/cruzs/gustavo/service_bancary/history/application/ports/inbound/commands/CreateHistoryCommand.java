package dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateHistoryCommand(
    UUID accountId,
    BigDecimal amount,
    String destinataryName,
    String institutionName
) {
}
