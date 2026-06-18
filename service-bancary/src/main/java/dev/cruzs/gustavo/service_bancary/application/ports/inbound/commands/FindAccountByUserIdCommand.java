package dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands;

import java.util.UUID;

public record FindAccountByUserIdCommand(UUID userId) {
}
