package dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands;

import dev.cruzs.gustavo.service_bancary.account.domain.valueObjects.NumberAccount;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferMoneyCommand(UUID senderUserId, BigDecimal amount, NumberAccount recipientNumberAccount) {
}
