package dev.cruzs.gustavo.service_bancary.application.ports.inbound.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawAccountCommand(UUID id, BigDecimal amount) {
}
