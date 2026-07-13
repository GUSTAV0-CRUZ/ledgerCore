package dev.cruzs.gustavo.order_service.application.ports.in;

import dev.cruzs.gustavo.order_service.application.ports.in.commands.RegisterUserCommand;

public interface RegisterUserUseCase {
  void execute(RegisterUserCommand registerUserCommand);
}
