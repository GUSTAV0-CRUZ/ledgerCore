package dev.cruzs.gustavo.gateway_service.filters.messages.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
    @NotBlank
    @Size(min = 3,  max = 32)
    String username,

    @Email
    @NotBlank
    String email,

    @NotBlank
    @Size(min = 8)
    String password
) {
}
