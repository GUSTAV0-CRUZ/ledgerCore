package dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.maps;

import dev.cruzs.gustavo.service_bancary.history.adapters.outbound.persistence.models.HistoryModel;
import dev.cruzs.gustavo.service_bancary.history.domain.History;

public class HistoryMap {
  public static HistoryModel mapToHistoryModel(History history) {
    return new HistoryModel(
        history.getId(),
        history.getAccountId(),
        history.getAmount(),
        history.getDestinataryName(),
        history.getInstitutionName(),
        history.getTransferDate()
    );
  }
  public static History mapToHistory(HistoryModel historyModel) {
    return History.restore(
        historyModel.getId(),
        historyModel.getAccountId(),
        historyModel.getAmount(),
        historyModel.getDestinataryName(),
        historyModel.getInstitutionName(),
        historyModel.getTransferDate()
    );
  }
}
