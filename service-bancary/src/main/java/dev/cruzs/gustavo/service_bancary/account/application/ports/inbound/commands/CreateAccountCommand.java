package dev.cruzs.gustavo.service_bancary.account.application.ports.inbound.commands;

import dev.cruzs.gustavo.service_bancary.account.domain.enums.AccountTypeEnum;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountCommand(
    UUID userId,
    Integer agency,
    String number,
    BigDecimal balance,
    AccountTypeEnum typeAccount
) {
}
