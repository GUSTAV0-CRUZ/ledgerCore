package dev.cruzs.gustavo.service_bancary.schedulingWork.application.services;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.DeleteSchedulingTransferUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.DeleteSchedulingTransferCommand;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingCache;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingRepository;

public class DeleteSchedulingTransferService implements DeleteSchedulingTransferUseCase {
  private final SchedulingRepository schedulingRepository;
  private final SchedulingCache schedulingCache;

  public DeleteSchedulingTransferService(SchedulingRepository schedulingRepository, SchedulingCache schedulingCache) {
    this.schedulingRepository = schedulingRepository;
    this.schedulingCache = schedulingCache;
  }

  @Override
  public void execute(DeleteSchedulingTransferCommand command) {
    schedulingRepository.deleteById(command.schedulingTransferId());
    schedulingCache.deleteById(command.schedulingTransferId());
  }
}
