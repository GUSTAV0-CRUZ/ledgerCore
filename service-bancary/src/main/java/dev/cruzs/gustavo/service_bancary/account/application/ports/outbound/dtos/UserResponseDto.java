package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos;

import java.util.UUID;

public record UserResponseDto(UUID id, String email, String name) {
}
