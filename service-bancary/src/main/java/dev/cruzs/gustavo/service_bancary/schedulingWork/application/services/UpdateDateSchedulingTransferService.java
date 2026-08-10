package dev.cruzs.gustavo.service_bancary.schedulingWork.application.services;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.UpdateDateSchedulingTransferUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.UpdateDateSchedulingTransferCommand;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingCache;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingRepository;
import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.Scheduling;
import jakarta.transaction.Transactional;

public class UpdateDateSchedulingTransferService implements UpdateDateSchedulingTransferUseCase {
  private final SchedulingRepository schedulingRepository;
  private final SchedulingCache schedulingCache;

  public UpdateDateSchedulingTransferService(
      SchedulingRepository schedulingRepository,
      SchedulingCache schedulingCache
  ) {
    this.schedulingRepository = schedulingRepository;
    this.schedulingCache = schedulingCache;
  }

  @Transactional
  @Override
  public void execute(UpdateDateSchedulingTransferCommand command) {
    Scheduling scheduling = schedulingRepository.findByIdOrIllegalArgumentException(command.schedulingTransferId());
    scheduling.updateScheduledDate(command.newScheduledDate());
    schedulingRepository.update(scheduling);
    schedulingCache.save(command.schedulingTransferId(), command.newScheduledDate());
  }
}
