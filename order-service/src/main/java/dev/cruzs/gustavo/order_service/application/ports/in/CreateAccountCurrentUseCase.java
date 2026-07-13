package dev.cruzs.gustavo.order_service.application.ports.in;

import dev.cruzs.gustavo.order_service.application.ports.in.commands.CreateAccountCurrentCommand;

public interface CreateAccountCurrentUseCase {
  void execute(CreateAccountCurrentCommand createAccountCommand);
}
