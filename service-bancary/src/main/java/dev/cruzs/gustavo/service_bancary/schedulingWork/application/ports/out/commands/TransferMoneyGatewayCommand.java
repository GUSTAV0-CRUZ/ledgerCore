package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.commands;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferMoneyGatewayCommand(UUID senderUserId, BigDecimal amount, String recipientNumberAccount) {
}
