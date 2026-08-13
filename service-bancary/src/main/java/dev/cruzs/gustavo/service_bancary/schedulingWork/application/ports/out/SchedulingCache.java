package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SchedulingCache {
  void save(UUID SchedulingId, Instant scheduledDate);
  List<UUID> findByScheduledDate(Instant scheduledDate);
  void deleteAllById(List<UUID> SchedulingIds);
  void deleteById(UUID SchedulingId);
}
