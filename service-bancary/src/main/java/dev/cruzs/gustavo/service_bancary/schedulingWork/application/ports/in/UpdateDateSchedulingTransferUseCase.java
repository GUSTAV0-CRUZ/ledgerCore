package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.UpdateDateSchedulingTransferCommand;

public interface UpdateDateSchedulingTransferUseCase {
  void execute(UpdateDateSchedulingTransferCommand updateDateSchedulingTransferCommand);
}
