package dev.cruzs.gustavo.service_bancary.user.application.services;

import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.CreateUserUseCase;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands.CreateUserCommand;
import dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.UserRepository;
import dev.cruzs.gustavo.service_bancary.user.domain.User;

public class CreateUserService implements CreateUserUseCase {
  private final UserRepository userRepository;

  public CreateUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(CreateUserCommand command) {
    User user = User.create(
        command.name(),
        command.dateOfBirth(),
        command.email(),
        command.password()
    );

    return this.userRepository.save(user);
  }
}
