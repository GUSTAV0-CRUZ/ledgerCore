package dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence;

import dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.maps.HistoryMap;
import dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.models.HistoryModel;
import dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.repositories.HistoryJpaRepository;
import dev.cruzs.gustavo.service_bancary.history.application.ports.outbound.HistoryRepository;
import dev.cruzs.gustavo.service_bancary.history.domain.History;
import dev.cruzs.gustavo.service_bancary.history.domain.exceptions.NotFoundHistoryException;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HistoryRepositoryAdapter implements HistoryRepository {
  private final HistoryJpaRepository historyJpaRepository;

  public HistoryRepositoryAdapter(HistoryJpaRepository historyJpaRepository) {
    this.historyJpaRepository = historyJpaRepository;
  }

  @Override
  public History save(History history) {
    HistoryModel historyModel = HistoryMap.mapToHistoryModel(history);
    historyJpaRepository.save(historyModel);

    return history;
  }

  @Override
  public List<History> findAllByAccountIdAndYearMonth(UUID accountId, YearMonth yearMonth) {
    LocalDateTime startOfMonth = yearMonth.atDay(1)
      .atStartOfDay();
    LocalDateTime endOfMonth = yearMonth.atEndOfMonth()
      .atTime(23, 59, 59, 999999999);

    return historyJpaRepository.findAllByAccountIdAndTransferDateBetween(
        accountId,
        startOfMonth,
        endOfMonth
      )
      .stream()
      .map(HistoryMap::mapToHistory)
      .toList();
  }

  @Override
  public Optional<History> findById(UUID id) throws NotFoundHistoryException {
    HistoryModel historyModel = historyJpaRepository.findById(id).orElse(null);

    if (historyModel == null) return Optional.empty();

    return Optional.of(HistoryMap.mapToHistory(historyModel));
  }
}
