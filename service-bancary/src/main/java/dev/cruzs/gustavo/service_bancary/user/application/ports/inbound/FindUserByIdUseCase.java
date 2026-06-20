package dev.cruzs.gustavo.service_bancary.user.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands.FindUserByIdCommand;
import dev.cruzs.gustavo.service_bancary.user.domain.User;

public interface FindUserByIdUseCase {
  User execute(FindUserByIdCommand command);
}
