package dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.repositories;

import dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.models.HistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface HistoryJpaRepository extends JpaRepository<HistoryModel, UUID> {
  List<HistoryModel> findAllByAccountIdAndTransferDateBetween(
    UUID accountId,
    LocalDateTime start,
    LocalDateTime end
  );
}
