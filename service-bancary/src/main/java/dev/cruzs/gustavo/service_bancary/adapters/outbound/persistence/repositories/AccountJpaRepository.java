package dev.cruzs.gustavo.service_bancary.adapters.outbound.persistence.repositories;

import dev.cruzs.gustavo.service_bancary.adapters.outbound.persistence.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountModel, UUID> {
}
