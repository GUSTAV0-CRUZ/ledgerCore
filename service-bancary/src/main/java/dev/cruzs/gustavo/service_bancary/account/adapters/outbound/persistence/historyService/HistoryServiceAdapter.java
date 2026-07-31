package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.persistence.historyService;

import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.HistoryService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.CreateHistoryCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class HistoryServiceAdapter implements HistoryService {
  private final StreamBridge streamBridge;
  private final Logger logger =  LoggerFactory.getLogger(HistoryServiceAdapter.class);

  public HistoryServiceAdapter(StreamBridge streamBridge) {
    this.streamBridge = streamBridge;
  }

  @Override
  public void create(CreateHistoryCommand createHistoryCommand) {
    this.streamBridge.send("createHistoryProducer-out-0", createHistoryCommand);
    this.logger.info("Try create one history");
  }
}
