package dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferMoneyCommand(UUID sender, BigDecimal amount, UUID recipient) {
}
