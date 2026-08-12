package dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.in;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.CheckSchedulingAndProcessUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.DeleteSchedulingTransferUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.SchedulingTransferUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.UpdateDateSchedulingTransferUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.DeleteSchedulingTransferCommand;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.SchedulingTransferCommand;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.UpdateDateSchedulingTransferCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SchedulingConsumerAdapter {
  private final Logger logger = LoggerFactory.getLogger(SchedulingConsumerAdapter.class);
  private final SchedulingTransferUseCase schedulingTransferUseCase;
  private final UpdateDateSchedulingTransferUseCase updateDateSchedulingTransferUseCase;
  private final DeleteSchedulingTransferUseCase deleteSchedulingTransferUseCase;

  public SchedulingConsumerAdapter(
      SchedulingTransferUseCase schedulingTransferUseCase,
      UpdateDateSchedulingTransferUseCase updateDateSchedulingTransferUseCase,
      DeleteSchedulingTransferUseCase deleteSchedulingTransferUseCase
  ) {
    this.schedulingTransferUseCase = schedulingTransferUseCase;
    this.updateDateSchedulingTransferUseCase = updateDateSchedulingTransferUseCase;
    this.deleteSchedulingTransferUseCase = deleteSchedulingTransferUseCase;
  }

   public void schedulingTransfer(SchedulingTransferCommand schedulingTransferCommand) {
     schedulingTransferUseCase.execute(schedulingTransferCommand);

     logger.info(
         "user with id: ({}) try scheduling transfer to: {}",
         schedulingTransferCommand.senderUserId(),
         schedulingTransferCommand.scheduledDate()
     );
   }

   public void updateDateSchedulingTransfer(UpdateDateSchedulingTransferCommand updateDateSchedulingTransferCommand) {
     updateDateSchedulingTransferUseCase.execute(updateDateSchedulingTransferCommand);

     logger.info(
         "try update scheduling of transfer with id: ({}) to new date: {}",
         updateDateSchedulingTransferCommand.schedulingTransferId(),
         updateDateSchedulingTransferCommand.newScheduledDate()
     );
   }

   public void deleteSchedulingTransfer(DeleteSchedulingTransferCommand deleteSchedulingTransferCommand) {
     deleteSchedulingTransferUseCase.execute(deleteSchedulingTransferCommand);

     logger.info(
         "try delete scheduling of transfer with id: ({})",
         deleteSchedulingTransferCommand.schedulingTransferId()
     );
   }
}
