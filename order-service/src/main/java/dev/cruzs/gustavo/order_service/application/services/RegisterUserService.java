package dev.cruzs.gustavo.order_service.application.services;

import dev.cruzs.gustavo.order_service.application.ports.in.RegisterUserUseCase;
import dev.cruzs.gustavo.order_service.application.ports.in.commands.RegisterUserCommand;
import dev.cruzs.gustavo.order_service.application.ports.out.UserService;
import dev.cruzs.gustavo.order_service.application.ports.out.SendNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterUserService implements RegisterUserUseCase {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
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

    this.logger.info(
        "Try register User with id ({}) and send email to: {}",
        registerUserCommand.id(),
        registerUserCommand.email()
    );
  }
}
