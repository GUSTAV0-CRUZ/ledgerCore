package dev.cruzs.gustavo.service_bancary.user.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands.CreateUserCommand;
import dev.cruzs.gustavo.service_bancary.user.domain.User;

public interface CreateUserUseCase {
  User execute(CreateUserCommand command);
}
