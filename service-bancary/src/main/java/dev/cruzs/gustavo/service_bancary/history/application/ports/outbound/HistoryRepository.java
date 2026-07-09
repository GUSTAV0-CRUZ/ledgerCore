package dev.cruzs.gustavo.service_bancary.history.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.history.domain.History;
import dev.cruzs.gustavo.service_bancary.history.domain.exceptions.NotFoundHistoryException;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistoryRepository {
   History save(History history);
   List<History> findAllByAccountIdAndYearMonth(UUID accountId, YearMonth yearMonth);
   Optional<History> findById(UUID id) throws NotFoundHistoryException;

   default History findByIdOrNotFoundExceptions(UUID id) {
     return this.findById(id)
         .orElseThrow(() -> new NotFoundHistoryException("History with id: " + id + " not found"));
   };
}
