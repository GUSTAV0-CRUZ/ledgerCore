package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.EvaluateCommand;

public interface antiFraudService {
  void evaluate(EvaluateCommand evaluateCommand);
}
