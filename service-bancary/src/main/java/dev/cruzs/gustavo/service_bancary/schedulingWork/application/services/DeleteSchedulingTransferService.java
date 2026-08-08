package dev.cruzs.gustavo.service_bancary.schedulingWork.application.services;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.DeleteSchedulingTransferUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.DeleteSchedulingTransferCommand;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingRepository;
import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.Scheduling;

public class DeleteSchedulingTransferService implements DeleteSchedulingTransferUseCase {
  private final SchedulingRepository schedulingRepository;

  public DeleteSchedulingTransferService(SchedulingRepository schedulingRepository) {
    this.schedulingRepository = schedulingRepository;
  }

  @Override
  public void execute(DeleteSchedulingTransferCommand command) {
    Scheduling scheduling = schedulingRepository.findByIdOrIllegalArgumentException(command.schedulingTransferId());
    schedulingRepository.deleteById(scheduling.getId());
  }
}
