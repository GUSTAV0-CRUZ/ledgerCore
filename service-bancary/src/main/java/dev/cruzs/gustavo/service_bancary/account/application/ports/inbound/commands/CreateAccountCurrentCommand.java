package dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountCurrentCommand(
    UUID userId,
    Integer agency,
    BigDecimal balance
) {
}
