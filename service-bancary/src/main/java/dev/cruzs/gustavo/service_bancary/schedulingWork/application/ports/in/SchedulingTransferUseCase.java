package dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.commands.TransferMoneyCommand;

public interface TransferMoney {
  void execute(TransferMoneyCommand TransferMoneyCommand);
}
