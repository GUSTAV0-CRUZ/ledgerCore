package dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.out.persistence;

import dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.out.persistence.maps.SchedulingMap;
import dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.out.persistence.repositories.SchedulingJpaRepository;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingRepository;
import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.Scheduling;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SchedulingRepositoryAdapter implements SchedulingRepository {
  private final SchedulingJpaRepository schedulingJpaRepository;

  @PersistenceContext
  private EntityManager entityManager;

  public SchedulingRepositoryAdapter(SchedulingJpaRepository schedulingJpaRepository) {
    this.schedulingJpaRepository = schedulingJpaRepository;
  }

  @Override
  public void insert(Scheduling scheduling) {
    entityManager.persist(SchedulingMap.toSchedulingModel(scheduling));
  }

  @Override
  public void update(Scheduling scheduling) {
    entityManager.merge(SchedulingMap.toSchedulingModel(scheduling));
  }

  @Override
  public Optional<Scheduling> findById(UUID id) {
    return schedulingJpaRepository.findById(id).map(SchedulingMap::toScheduling);
  }

  @Override
  public void deleteById(UUID id) {
    schedulingJpaRepository.deleteById(id);
  }
}
