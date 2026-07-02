package dev.cruzs.gustavo.service_bancary.user.adapters.inbound.messages.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CreateUserDto(
    @NotBlank
    UUID id,

    @NotBlank
    String name,

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    String dateOfBirth,

    @NotBlank
    @Email
    String email,

    @NotBlank
    String cpf
) {
}
