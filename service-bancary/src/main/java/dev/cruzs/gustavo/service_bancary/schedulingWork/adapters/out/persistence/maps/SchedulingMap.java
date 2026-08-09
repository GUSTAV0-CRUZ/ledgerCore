package dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.out.persistence.maps;

import dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.out.persistence.models.SchedulingModel;
import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.Scheduling;

public class SchedulingMap {
  public static Scheduling toScheduling (SchedulingModel schedulingModel) {
    return Scheduling.restore(
        schedulingModel.getId(),
        schedulingModel.getSenderUserId(),
        schedulingModel.getAmount(),
        schedulingModel.getRecipientNumberAccount(),
        schedulingModel.getScheduledDate(),
        schedulingModel.getStatus()
    );
  };

  public static SchedulingModel toSchedulingModel (Scheduling scheduling) {
    return new SchedulingModel(
        scheduling.getId(),
        scheduling.getSenderUserId(),
        scheduling.getAmount(),
        scheduling.getRecipientNumberAccount(),
        scheduling.getScheduledDate(),
        scheduling.getStatus()
    );
  }
}
