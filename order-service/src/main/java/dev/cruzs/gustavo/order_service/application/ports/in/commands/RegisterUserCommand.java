package dev.cruzs.gustavo.order_service.application.ports.in.commands;

import java.time.LocalDate;
import java.util.UUID;

public record RegisterUserCommand(UUID id, String name, LocalDate dateOfBirth, String email, String cpf) {
}
