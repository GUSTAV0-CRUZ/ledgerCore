package dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.configs;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.CheckSchedulingAndProcessUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.DeleteSchedulingTransferUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.SchedulingTransferUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.UpdateDateSchedulingTransferUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingCache;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingRepository;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.services.CheckSchedulingAndProcessService;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.services.DeleteSchedulingTransferService;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.services.SchedulingTransferService;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.services.UpdateDateSchedulingTransferService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesSchedulingConfig {
  private final SchedulingRepository schedulingRepository;
  private final SchedulingCache schedulingCache;

  public UseCasesSchedulingConfig(SchedulingRepository schedulingRepository, SchedulingCache schedulingCache) {
    this.schedulingRepository = schedulingRepository;
    this.schedulingCache = schedulingCache;
  }

  @Bean
  public SchedulingTransferUseCase schedulingTransferUseCase() {
    return new SchedulingTransferService(schedulingRepository, schedulingCache);
  }

  @Bean
  public UpdateDateSchedulingTransferUseCase updateDateSchedulingTransferUseCase() {
    return new UpdateDateSchedulingTransferService(schedulingRepository, schedulingCache);
  }

  @Bean
  public DeleteSchedulingTransferUseCase deleteSchedulingTransferUseCase() {
    return new DeleteSchedulingTransferService(schedulingRepository, schedulingCache);
  }

  @Bean
  public CheckSchedulingAndProcessUseCase checkSchedulingAndProcessUseCase() {
    return new CheckSchedulingAndProcessService(schedulingRepository, schedulingCache);
  }
}
