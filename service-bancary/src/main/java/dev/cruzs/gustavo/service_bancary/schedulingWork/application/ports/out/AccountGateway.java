package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.commands.TransferMoneyGatewayCommand;

public interface AccountGateway {
  void transferMoney(TransferMoneyGatewayCommand transferMoneyGatewayCommand);
}
