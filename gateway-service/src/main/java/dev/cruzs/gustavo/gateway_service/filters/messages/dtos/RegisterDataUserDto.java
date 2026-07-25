package dev.cruzs.gustavo.gateway_service.filters.messages.dtos;

import java.util.UUID;

public record RegisterDataUserDto(UUID id, String name, String dateOfBirth, String email, String cpf) {
}
