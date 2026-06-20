package dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands;

import java.time.LocalDate;

public record CreateUserCommand(String name, LocalDate dateOfBirth, String email, String password) {
}
