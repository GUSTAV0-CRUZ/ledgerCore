package dev.cruzs.gustavo.auth_service.application.ports.in.commands;

import dev.cruzs.gustavo.auth_service.domain.valueObjects.Email;

public record CreateUserCommand(
    String username,
    Email email,
    String password
) {
}
