package dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.repositories;

import dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.models.HistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface HistoryJpaRepository extends JpaRepository<HistoryModel, UUID> {
  @Query("SELECT h FROM HistoryModel h WHERE (h.accountId = :accountId OR h.destinataryName = :destinataryName) AND h.transferDate BETWEEN :start AND :end")
  List<HistoryModel> findAllByAccountIdOrDestinataryNameAndTransferDateBetween(
      UUID accountId,
      String destinataryName,
      Instant start,
      Instant end
  );
}
