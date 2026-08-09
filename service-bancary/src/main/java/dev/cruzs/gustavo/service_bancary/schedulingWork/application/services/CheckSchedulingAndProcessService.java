package dev.cruzs.gustavo.service_bancary.schedulingWork.application.services;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.CheckSchedulingAndProcessUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingCache;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingRepository;
import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.enums.SchedulingEnum;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CheckSchedulingAndProcessService implements CheckSchedulingAndProcessUseCase {
  private final SchedulingRepository schedulingRepository;
  private final SchedulingCache schedulingCache;

  public CheckSchedulingAndProcessService(SchedulingRepository schedulingRepository, SchedulingCache schedulingCache) {
    this.schedulingRepository = schedulingRepository;
    this.schedulingCache = schedulingCache;
  }

  @Transactional
  @Override
  public void execute() {
    LocalDateTime now = LocalDateTime.now();

    List<UUID> schedulingCacheListUuid = schedulingCache.findByScheduledDate(now);

    if (schedulingCacheListUuid.isEmpty()) return;

    schedulingCacheListUuid.forEach(uuid -> {
      schedulingRepository.findById(uuid).ifPresent(scheduling -> {
        scheduling.updateSchedulingType(SchedulingEnum.PROCESSED);
        schedulingRepository.update(scheduling);
      });
    });

    schedulingCache.deleteAll(schedulingCacheListUuid);
  }
}
