package dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.in;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.CheckSchedulingAndProcessUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SchedulingCronAdapter {
  private final Logger logger = LoggerFactory.getLogger(SchedulingCronAdapter.class);
  private final CheckSchedulingAndProcessUseCase checkSchedulingAndProcessUseCase;

  public SchedulingCronAdapter(CheckSchedulingAndProcessUseCase checkSchedulingAndProcessUseCase) {
    this.checkSchedulingAndProcessUseCase = checkSchedulingAndProcessUseCase;
  }

  @Scheduled(cron = "0 * * * * *")
  public void checkSchedulingAndProcess() {
    checkSchedulingAndProcessUseCase.execute();

    logger.info("check scheduling and process the {}", LocalDateTime.now());
  }
}
