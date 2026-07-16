package dev.cruzs.gustavo.auth_service.application.ports.out.commands;

import java.util.UUID;

public record GenerateJwtCommand(UUID id, String userName, String email) {
}
