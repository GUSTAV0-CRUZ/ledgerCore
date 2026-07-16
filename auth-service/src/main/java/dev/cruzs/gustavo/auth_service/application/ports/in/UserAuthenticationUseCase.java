package dev.cruzs.gustavo.auth_service.application.ports.in;

import dev.cruzs.gustavo.auth_service.application.ports.in.commands.UserAuthenticationCommand;

public interface UserAuthenticationUseCase {
  String execute(UserAuthenticationCommand userAuthenticationCommand);
}
