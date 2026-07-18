package dev.cruzs.gustavo.auth_service.application.ports.out;

import dev.cruzs.gustavo.auth_service.domain.User;
import dev.cruzs.gustavo.auth_service.domain.exceptions.NotFoundException;
import dev.cruzs.gustavo.auth_service.domain.valueObjects.Email;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
  User save(User user);
  Optional<User> findById(UUID id);
  Optional<User> findByEmail(Email email);

  default User findByEmailOrNotFoundException(Email email) {
    User user = this.findByEmail(email).orElse(null);

    if (user == null) throw new NotFoundException("User not found");

    return user;
  }
}
