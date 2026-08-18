package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record EvaluateCommand(UUID accountId, String destinataryName, BigDecimal amount) {
}
