package dev.cruzs.gustavo.auth_service.application.services;

import dev.cruzs.gustavo.auth_service.application.ports.in.CreateUserUseCase;
import dev.cruzs.gustavo.auth_service.application.ports.in.commands.CreateUserCommand;
import dev.cruzs.gustavo.auth_service.application.ports.out.Hash;
import dev.cruzs.gustavo.auth_service.application.ports.out.UserRepository;
import dev.cruzs.gustavo.auth_service.domain.User;

public class CreateUserService implements CreateUserUseCase {
  private final UserRepository userRepository;
  private final Hash hash;

  public CreateUserService(UserRepository userRepository, Hash hash) {
    this.userRepository = userRepository;
    this.hash = hash;
  }

  @Override
  public User execute(CreateUserCommand createUserCommand) {
    String passwordWithHash = this.hash.generate(createUserCommand.password());

    User user = User.create(
        createUserCommand.username(),
        createUserCommand.email(),
        passwordWithHash
    );

    return this.userRepository.save(user);
  }
}
