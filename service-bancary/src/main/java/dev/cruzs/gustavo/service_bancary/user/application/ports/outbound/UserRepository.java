package dev.cruzs.gustavo.service_bancary.user.application.ports.outbound;

import dev.cruzs.gustavo.service_bancary.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
  User save(User user);
  Optional<User> findById(UUID id);
}
