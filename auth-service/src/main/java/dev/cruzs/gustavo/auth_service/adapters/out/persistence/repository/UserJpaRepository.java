package dev.cruzs.gustavo.auth_service.adapters.out.persistence.repository;

import dev.cruzs.gustavo.auth_service.adapters.out.persistence.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserModel, UUID> {
  Optional<UserModel> findByEmail(String email);
}
