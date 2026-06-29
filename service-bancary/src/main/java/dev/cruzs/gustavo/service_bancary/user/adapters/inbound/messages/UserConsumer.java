package dev.cruzs.gustavo.service_bancary.user.adapters.inbound.messages;

import dev.cruzs.gustavo.service_bancary.user.adapters.inbound.messages.dtos.CreateUserDto;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.CreateUserUseCase;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands.CreateUserCommand;
import dev.cruzs.gustavo.service_bancary.user.domain.User;

import java.time.LocalDate;
import java.util.function.Consumer;

public class UserConsumer {
  private final CreateUserUseCase createUserUseCase;

  public UserConsumer(CreateUserUseCase createUserUseCase) {
    this.createUserUseCase = createUserUseCase;
  }

  public Consumer<CreateUserDto> createUserConsumer() {
    return createUserDto -> {
      LocalDate dateOfBirth = LocalDate.parse(createUserDto.dateOfBirth());

      CreateUserCommand createUserCommand = new CreateUserCommand(
          createUserDto.name(),
          dateOfBirth,
          createUserDto.email(),
          createUserDto.password()
      );
      User user = createUserUseCase.execute(createUserCommand);

      System.out.println("Create user with id: " + user.getId());
    };
  }
}
