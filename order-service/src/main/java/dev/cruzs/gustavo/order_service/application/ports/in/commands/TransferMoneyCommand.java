package dev.cruzs.gustavo.order_service.application.ports.in.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferMoneyCommand(UUID sender, BigDecimal amount, UUID recipient) {
}
