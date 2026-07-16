package dev.cruzs.gustavo.auth_service.application.ports.out;

import dev.cruzs.gustavo.auth_service.domain.User;
import dev.cruzs.gustavo.auth_service.domain.exceptions.NotFoundException;

import java.util.Optional;

public interface UserRepository {
  User save(User user);
  Optional<User> findById(Long id);
  Optional<User> findByEmail(String email);

  default User findByEmailOrNotFoundException(String email) {
    User user = this.findByEmail(email).orElse(null);

    if (user == null) throw new NotFoundException("User not found");

    return user;
  }
}
