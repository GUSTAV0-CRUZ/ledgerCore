package dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateHistoryCommand(
    UUID accountId,
    String destinataryName,
    String institutionName
) {
}
