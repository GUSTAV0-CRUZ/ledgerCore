package dev.cruzs.gustavo.service_bancary.history.application.services;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.FindAllByAccountIdOrDestinataryNameAndYearMonthUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.FindAllByAccountIdOrDestinataryNameAndYearMonthCommand;
import dev.cruzs.gustavo.service_bancary.history.application.ports.outbound.HistoryRepository;
import dev.cruzs.gustavo.service_bancary.history.domain.History;

import java.time.YearMonth;
import java.util.List;

public class FindAllByAccountIdOrDestinataryNameAndYearMonthService implements FindAllByAccountIdOrDestinataryNameAndYearMonthUseCase {
  private final HistoryRepository historyRepository;

  public FindAllByAccountIdOrDestinataryNameAndYearMonthService(HistoryRepository historyRepository) {
    this.historyRepository = historyRepository;
  }

  @Override
  public List<History> execute(FindAllByAccountIdOrDestinataryNameAndYearMonthCommand command) {
    return historyRepository.findAllByAccountIdOrDestinataryNameAndYearMonth(
      command.accountId(),
      command.destinataryName(),
      command.yearMonth() != null ? command.yearMonth() : YearMonth.now()
    );
  }
}
