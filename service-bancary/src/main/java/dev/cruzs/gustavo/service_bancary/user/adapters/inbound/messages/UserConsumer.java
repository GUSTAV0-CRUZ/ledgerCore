package dev.cruzs.gustavo.service_bancary.user.adapters.inbound.messages;

import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.CreateUserUseCase;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands.CreateUserCommand;
import dev.cruzs.gustavo.service_bancary.user.domain.User;

import java.util.function.Consumer;

public class UserConsumer {
  private final CreateUserUseCase createUserUseCase;

  public UserConsumer(CreateUserUseCase createUserUseCase) {
    this.createUserUseCase = createUserUseCase;
  }

  public Consumer<CreateUserCommand> createUserConsumer() {
    return createUserCommand -> {
      User user = createUserUseCase.execute(createUserCommand);

      System.out.println("Create user with id: " + user.getId());
    };
  }
}
