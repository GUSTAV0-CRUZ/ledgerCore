package dev.cruzs.gustavo.service_bancary.adapters.outbound.persistence.repositories;

import dev.cruzs.gustavo.service_bancary.adapters.outbound.persistence.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountModel, UUID> {
  @Transactional
  @Modifying
  @Query("UPDATE AccountModel a SET a.balance = :balance WHERE a.id = :id")
  int updateBalance(@Param("id") UUID id, @Param("balance") BigDecimal balance);
}
