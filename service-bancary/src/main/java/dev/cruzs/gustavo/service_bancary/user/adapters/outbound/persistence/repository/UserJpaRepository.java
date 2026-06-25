package dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence.repository;

import dev.cruzs.gustavo.service_bancary.user.adapters.outbound.persistence.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserModel, UUID> {
}
