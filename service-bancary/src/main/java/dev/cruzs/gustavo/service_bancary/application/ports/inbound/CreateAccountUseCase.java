package dev.cruzs.gustavo.service_bancary.application.ports.inbound;

import dev.cruzs.gustavo.service_bancary.application.ports.inbound.dtos.CreateAccountDto;
import dev.cruzs.gustavo.service_bancary.domain.Account;

public interface CreateAccountUseCase {
  Account execute(CreateAccountDto createAccountDto);
}
