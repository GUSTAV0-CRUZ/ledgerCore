package dev.cruzs.gustavo.service_bancary.history.application.services;

import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.FindAllByAccountIdAndYearMonthUseCase;
import dev.cruzs.gustavo.service_bancary.history.application.ports.inbound.commands.FindAllByAccountIdAndYearMonthCommand;
import dev.cruzs.gustavo.service_bancary.history.application.ports.outbound.HistoryRepository;
import dev.cruzs.gustavo.service_bancary.history.domain.History;

import java.time.YearMonth;
import java.util.List;

public class FindAllByAccountIdAndYearMonthService implements FindAllByAccountIdAndYearMonthUseCase {
  private final HistoryRepository historyRepository;

  public FindAllByAccountIdAndYearMonthService(HistoryRepository historyRepository) {
    this.historyRepository = historyRepository;
  }

  @Override
  public List<History> execute(FindAllByAccountIdAndYearMonthCommand command) {
    return historyRepository.findAllByAccountIdAndYearMonth(
      command.accountId(),
      command.yearMonth() != null ? command.yearMonth() : YearMonth.now()
    );
  }
}
