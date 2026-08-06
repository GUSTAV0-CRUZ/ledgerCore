package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out;

import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.Scheduling;

import java.util.Optional;
import java.util.UUID;

public interface SchedulingRepository {
  Scheduling save(Scheduling scheduling);
  Optional<Scheduling> findById(UUID id);

  default Scheduling findByIdOrIllegalArgumentException(UUID id) {
    return this.findById(id).orElseThrow(() -> new IllegalArgumentException("Scheduling not found"));
  }
}
