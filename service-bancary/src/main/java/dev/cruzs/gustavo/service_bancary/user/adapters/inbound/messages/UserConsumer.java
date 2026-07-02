package dev.cruzs.gustavo.service_bancary.user.adapters.inbound.messages;

import dev.cruzs.gustavo.service_bancary.user.adapters.inbound.messages.dtos.CreateUserDto;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.CreateUserUseCase;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands.CreateUserCommand;
import dev.cruzs.gustavo.service_bancary.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.function.Consumer;

public class UserConsumer {
  private final Logger logger = LoggerFactory.getLogger(UserConsumer.class);
  private final CreateUserUseCase createUserUseCase;

  public UserConsumer(CreateUserUseCase createUserUseCase) {
    this.createUserUseCase = createUserUseCase;
  }

  public Consumer<CreateUserDto> createUserConsumer() {
    return createUserDto -> {
      LocalDate dateOfBirth = LocalDate.parse(createUserDto.dateOfBirth());

      CreateUserCommand createUserCommand = new CreateUserCommand(
          createUserDto.id(),
          createUserDto.name(),
          dateOfBirth,
          createUserDto.email(),
          createUserDto.cpf()
      );
      User user = createUserUseCase.execute(createUserCommand);

      logger.info("Create user with id: {}", user.getId());
    };
  }
}
