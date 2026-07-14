package dev.cruzs.gustavo.order_service.application.ports.in;

import dev.cruzs.gustavo.order_service.application.ports.in.commands.TransferMoneyCommand;

public interface TransferMoneyUseCase {
  void execute(TransferMoneyCommand transferMoneyCommand);
}
