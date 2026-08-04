package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.UserResponseDto;

import java.util.UUID;

public interface UserService {
  UserResponseDto findById(UUID userId);
}
