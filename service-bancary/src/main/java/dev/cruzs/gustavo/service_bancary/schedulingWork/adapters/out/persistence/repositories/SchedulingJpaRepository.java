package dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.out.persistence.repositories;

import dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.out.persistence.models.SchedulingModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchedulingJpaRepository extends JpaRepository<SchedulingModel, UUID> {
}
