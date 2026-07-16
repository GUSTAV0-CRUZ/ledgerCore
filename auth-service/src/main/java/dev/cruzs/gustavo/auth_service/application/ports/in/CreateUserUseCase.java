package dev.cruzs.gustavo.auth_service.application.ports.in;

import dev.cruzs.gustavo.auth_service.application.ports.in.commands.CreateUserCommand;
import dev.cruzs.gustavo.auth_service.domain.User;

public interface CreateUserUseCase {
  User execute(CreateUserCommand createUserCommand);
}
