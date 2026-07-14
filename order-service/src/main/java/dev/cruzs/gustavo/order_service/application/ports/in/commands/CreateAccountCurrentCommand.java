package dev.cruzs.gustavo.order_service.application.ports.in.commands;

import java.util.UUID;

public record CreateAccountCurrentCommand(
    UUID userId
) {
}
