package dev.cruzs.gustavo.gateway_service.filters.messages.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record RegisterDataUserRequestDto(
  @NotBlank
  String name,

  @NotBlank
  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
  String dateOfBirth,

  @NotBlank
  String cpf
) {
}
