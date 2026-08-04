package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.notification;

import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.NotificationService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.SendEmailCommand;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class notificationServiceAdapter implements NotificationService {
  private final StreamBridge streamBridge;

  public notificationServiceAdapter(StreamBridge streamBridge) {
    this.streamBridge = streamBridge;
  }

  @Override
  public void sendEmail(SendEmailCommand sendEmailCommand) {
    streamBridge.send("sendEmailProducer-out-0", sendEmailCommand);
  }
}
