package dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands;

import java.util.UUID;

public record FindAllHistoryByAccountIdCommand(UUID accountId) {
}
