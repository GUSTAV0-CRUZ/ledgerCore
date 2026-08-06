package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.DeleteSchedulingTransferCommand;

public interface DeleteSchedulingTransferUseCase {
  void execute(DeleteSchedulingTransferCommand deleteSchedulingTransferCommand);
}
