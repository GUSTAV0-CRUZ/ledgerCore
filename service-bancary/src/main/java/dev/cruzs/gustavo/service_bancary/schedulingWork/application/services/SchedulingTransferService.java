package dev.cruzs.gustavo.service_bancary.schedulingWork.application.services;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.SchedulingTransferUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.SchedulingTransferCommand;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingCache;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingRepository;
import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.Scheduling;
import jakarta.transaction.Transactional;

public class SchedulingTransferService implements SchedulingTransferUseCase {
  private final SchedulingRepository schedulingRepository;
  private final SchedulingCache schedulingCache;

  public SchedulingTransferService(SchedulingRepository schedulingRepository, SchedulingCache schedulingCache) {
    this.schedulingRepository = schedulingRepository;
    this.schedulingCache = schedulingCache;
  }

  @Transactional
  @Override
  public void execute(SchedulingTransferCommand schedulingTransferCommand) {
    Scheduling scheduling = Scheduling.create(
        schedulingTransferCommand.senderUserId(),
        schedulingTransferCommand.amount(),
        schedulingTransferCommand.scheduledDate(),
        schedulingTransferCommand.recipientNumberAccount()
    );

    schedulingRepository.insert(scheduling);
    schedulingCache.save(scheduling.getId(), scheduling.getScheduledDate());
  }
}
