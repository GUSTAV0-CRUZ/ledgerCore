package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.SchedulingTransferCommand;

public interface SchedulingTransferUseCase {
  void execute(SchedulingTransferCommand SchedulingTransferCommand);
}
