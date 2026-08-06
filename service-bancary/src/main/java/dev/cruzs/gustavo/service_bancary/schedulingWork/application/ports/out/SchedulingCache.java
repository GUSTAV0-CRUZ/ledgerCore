package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SchedulingCache {
  void save(UUID SchedulingId, LocalDateTime scheduledDate);
  List<UUID> findByScheduledDate(LocalDateTime scheduledDate);
  void deleteAll(List<UUID> SchedulingIds);
}
