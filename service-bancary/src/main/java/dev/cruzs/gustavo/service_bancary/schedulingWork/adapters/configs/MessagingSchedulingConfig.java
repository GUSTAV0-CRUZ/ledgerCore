package dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.configs;

import dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.in.SchedulingConsumerAdapter;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.DeleteSchedulingTransferCommand;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.SchedulingTransferCommand;
import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.in.commands.UpdateDateSchedulingTransferCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessagingSchedulingConfig {
  private final SchedulingConsumerAdapter schedulingConsumerAdapter;

  public MessagingSchedulingConfig(SchedulingConsumerAdapter schedulingConsumerAdapter) {
    this.schedulingConsumerAdapter = schedulingConsumerAdapter;
  }

  @Bean
  public Consumer<SchedulingTransferCommand> schedulingTransferConsumer() {
    return schedulingConsumerAdapter::schedulingTransfer;
  }

  @Bean
  public Consumer<UpdateDateSchedulingTransferCommand> updateDateSchedulingTransferConsumer() {
    return schedulingConsumerAdapter::updateDateSchedulingTransfer;
  }

  @Bean
  public Consumer<DeleteSchedulingTransferCommand> deleteSchedulingTransferConsumer() {
    return schedulingConsumerAdapter::deleteSchedulingTransfer;
  }
}
