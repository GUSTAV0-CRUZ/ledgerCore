package dev.cruzs.gustavo.auth_service.adapters.in.messaging;

import dev.cruzs.gustavo.auth_service.adapters.in.dtos.CreateUserRequestDto;
import dev.cruzs.gustavo.auth_service.application.ports.in.CreateUserUseCase;
import dev.cruzs.gustavo.auth_service.application.ports.in.commands.CreateUserCommand;
import dev.cruzs.gustavo.auth_service.domain.User;
import dev.cruzs.gustavo.auth_service.domain.valueObjects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {
  private final Logger logger = LoggerFactory.getLogger(UserConsumer.class);
  private final CreateUserUseCase createUserUseCase;

  public UserConsumer(CreateUserUseCase createUserUseCase) {
    this.createUserUseCase = createUserUseCase;
  }

  public void createUserConsumer(CreateUserRequestDto createUserRequestDto) {
    CreateUserCommand createUserCommand = new CreateUserCommand(
        createUserRequestDto.username(),
        new Email(createUserRequestDto.email()),
        createUserRequestDto.password()
    );
    User user = this.createUserUseCase.execute(createUserCommand);

    this.logger.info("create user with id: ({})", user.getId());
  }
}
