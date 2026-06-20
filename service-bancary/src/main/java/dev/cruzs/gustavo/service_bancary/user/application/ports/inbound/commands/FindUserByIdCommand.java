package dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands;

import java.util.UUID;

public record FindUserByIdCommand(UUID id) {
}
