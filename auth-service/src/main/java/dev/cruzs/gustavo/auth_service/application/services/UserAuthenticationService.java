package dev.cruzs.gustavo.auth_service.application.services;

import dev.cruzs.gustavo.auth_service.application.ports.in.UserAuthenticationUseCase;
import dev.cruzs.gustavo.auth_service.application.ports.in.commands.UserAuthenticationCommand;
import dev.cruzs.gustavo.auth_service.application.ports.out.Hash;
import dev.cruzs.gustavo.auth_service.application.ports.out.Jwt;
import dev.cruzs.gustavo.auth_service.application.ports.out.UserRepository;
import dev.cruzs.gustavo.auth_service.application.ports.out.commands.GenerateJwtCommand;
import dev.cruzs.gustavo.auth_service.domain.User;

public class UserAuthenticationService implements UserAuthenticationUseCase {
  private final UserRepository userRepository;
  private final Jwt jwt;
  private final Hash hash;

  public UserAuthenticationService(UserRepository userRepository, Jwt jwt, Hash hash) {
    this.userRepository = userRepository;
    this.jwt = jwt;
    this.hash = hash;
  }

  @Override
  public String execute(UserAuthenticationCommand userAuthenticationCommand) {
    User user = this.userRepository.findByEmailOrNotFoundException(userAuthenticationCommand.email());

    if (!this.hash.validate(userAuthenticationCommand.password(), user.getPassword()))
      throw new IllegalArgumentException("Invalid password");

    return this.jwt.generate(new GenerateJwtCommand(
        user.getId(),
        user.getUsername(),
        user.getEmail().getValue()
    ));
  }
}
