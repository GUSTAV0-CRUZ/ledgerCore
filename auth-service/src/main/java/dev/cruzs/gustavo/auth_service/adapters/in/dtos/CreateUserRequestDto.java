package dev.cruzs.gustavo.auth_service.adapters.in.dtos;

import dev.cruzs.gustavo.auth_service.domain.valueObjects.Email;

public record CreateUserRequestDto(String username, String email, String password) {
}
