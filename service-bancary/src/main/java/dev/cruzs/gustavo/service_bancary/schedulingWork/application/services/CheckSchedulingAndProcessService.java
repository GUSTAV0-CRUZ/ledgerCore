package dev.cruzs.gustavo.service_bancary.schedulingWork.application.services;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.CheckSchedulingAndProcessUseCase;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.AccountGateway;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingCache;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingRepository;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.commands.TransferMoneyGatewayCommand;
import dev.cruzs.gustavo.service_bancary.schedulingWork.domain.enums.SchedulingEnum;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CheckSchedulingAndProcessService implements CheckSchedulingAndProcessUseCase {
  private final SchedulingRepository schedulingRepository;
  private final SchedulingCache schedulingCache;
  private final AccountGateway accountGateway;

  public CheckSchedulingAndProcessService(
      SchedulingRepository schedulingRepository,
      SchedulingCache schedulingCache,
      AccountGateway accountGateway
  ) {
    this.schedulingRepository = schedulingRepository;
    this.schedulingCache = schedulingCache;
    this.accountGateway = accountGateway;
  }

  @Transactional
  @Override
  public void execute() {
    Instant now = Instant.now();

    List<UUID> schedulingCacheListUuid = schedulingCache.findByScheduledDate(now);

    if (schedulingCacheListUuid.isEmpty()) return;

    schedulingCacheListUuid.forEach(uuid -> {
      schedulingRepository.findById(uuid).ifPresent(scheduling -> {
        accountGateway.transferMoney(
            new TransferMoneyGatewayCommand(
              scheduling.getSenderUserId(),
              scheduling.getAmount(),
              scheduling.getRecipientNumberAccount()
            )
        );

        scheduling.updateSchedulingStatus(SchedulingEnum.PROCESSED);
        schedulingRepository.update(scheduling);
      });
    });

    schedulingCache.deleteAllById(schedulingCacheListUuid);
  }
}
