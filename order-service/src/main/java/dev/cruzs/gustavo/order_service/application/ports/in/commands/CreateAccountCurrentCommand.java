package dev.cruzs.gustavo.order_service.application.ports.in.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountCurrentCommand(
    UUID userId,
    BigDecimal balance
) {
}
