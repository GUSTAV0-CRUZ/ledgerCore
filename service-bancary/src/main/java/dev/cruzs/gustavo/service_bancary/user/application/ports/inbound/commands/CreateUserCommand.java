package dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands;

import java.time.LocalDate;
import java.util.UUID;

public record CreateUserCommand(UUID id, String name, LocalDate dateOfBirth, String email, String cpf) {
}
