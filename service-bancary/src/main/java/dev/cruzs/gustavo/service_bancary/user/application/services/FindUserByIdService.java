package dev.cruzs.gustavo.service_bancary.user.application.services;

import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.FindUserByIdUseCase;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands.FindUserByIdCommand;
import dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.UserRepository;
import dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.exceptions.NotFoundException;
import dev.cruzs.gustavo.service_bancary.user.domain.User;

public class FindUserByIdService implements FindUserByIdUseCase {
  private final UserRepository userRepository;
  public FindUserByIdService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(FindUserByIdCommand command) {
    return this.userRepository.findById(command.id())
        .orElseThrow(() -> new NotFoundException("User not found"));
  }
}
