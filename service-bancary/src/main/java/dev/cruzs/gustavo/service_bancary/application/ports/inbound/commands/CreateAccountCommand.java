package dev.cruzs.gustavo.service_bancary.application.ports.inbound.dtos;

import dev.cruzs.gustavo.service_bancary.domain.enums.AccountTypeEnum;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountDto(
    UUID userId,
    Integer agency,
    String number,
    BigDecimal balance,
    AccountTypeEnum typeAccount
) {
}
