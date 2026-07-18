package dev.cruzs.gustavo.auth_service.application.ports.in.commands;

import dev.cruzs.gustavo.auth_service.domain.valueObjects.Email;

public record UserAuthenticationCommand(Email email, String password) {
}
