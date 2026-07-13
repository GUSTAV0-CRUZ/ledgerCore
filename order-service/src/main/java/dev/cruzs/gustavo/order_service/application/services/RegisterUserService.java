package dev.cruzs.gustavo.order_service.application.services;

import dev.cruzs.gustavo.order_service.application.ports.in.RegisterUserUseCase;
import dev.cruzs.gustavo.order_service.application.ports.in.commands.RegisterUserCommand;
import dev.cruzs.gustavo.order_service.application.ports.out.UserService;
import dev.cruzs.gustavo.order_service.application.ports.out.SendNotification;

public class RegisterUserService implements RegisterUserUseCase {
  private final UserService userService;
  private final SendNotification sendNotification;

  public RegisterUserService(UserService userService, SendNotification sendNotification) {
    this.userService = userService;
    this.sendNotification = sendNotification;
  }

  @Override
  public void execute(RegisterUserCommand registerUserCommand) {
    this.userService.createUser(
      registerUserCommand.id(),
      registerUserCommand.name(),
      registerUserCommand.dateOfBirth(),
      registerUserCommand.email(),
      registerUserCommand.cpf()
    );

    this.sendNotification.sendEmail(
        registerUserCommand.email(),
        "Register User",
        "Your data is being analyzed, please check back later to continue."
    );
  }
}
