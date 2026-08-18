package dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands;

import java.time.YearMonth;
import java.util.UUID;

public record FindAllByAccountIdOrDestinataryNameAndYearMonthCommand(UUID accountId, String destinataryName, YearMonth yearMonth) {
}
