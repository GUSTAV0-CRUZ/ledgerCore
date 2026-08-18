package dev.cruzs.gustavo.service_bancary.history.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.FindAllByAccountIdOrDestinataryNameAndYearMonthCommand;
import dev.cruzs.gustavo.service_bancary.history.domain.History;

import java.util.List;

public interface FindAllByAccountIdOrDestinataryNameAndYearMonthUseCase {
  List<History> execute(FindAllByAccountIdOrDestinataryNameAndYearMonthCommand command);
}
