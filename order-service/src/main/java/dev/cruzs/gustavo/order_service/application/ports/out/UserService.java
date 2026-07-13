package dev.cruzs.gustavo.order_service.application.ports.out;

import java.time.LocalDate;
import java.util.UUID;

public interface UserService {
  void createUser(UUID id, String name, LocalDate dateOfBirth, String email, String cpf);
}
