package dev.cruzs.gustavo.service_bancary.user.application.services;

import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.CreateUserUseCase;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands.CreateUserCommand;
import dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.Hashing;
import dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.UserRepository;
import dev.cruzs.gustavo.service_bancary.user.domain.User;

public class CreateUserService implements CreateUserUseCase {
  private final UserRepository userRepository;
  private final Hashing hashing;

  public CreateUserService(UserRepository userRepository, Hashing hashing) {
    this.userRepository = userRepository;
    this.hashing = hashing;
  }

  @Override
  public User execute(CreateUserCommand command) {
    String passwordHash = hashing.hash(command.password());

    User user = User.create(
        command.name(),
        command.dateOfBirth(),
        command.email(),
        passwordHash
    );

    return this.userRepository.save(user);
  }
}
