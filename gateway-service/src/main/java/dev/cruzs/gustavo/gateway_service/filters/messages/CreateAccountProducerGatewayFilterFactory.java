package dev.cruzs.gustavo.gateway_service.filters.messages;

import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.CreateAccountDto;
import dev.cruzs.gustavo.gateway_service.utils.GetHeadersOfRequest;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateAccountProducerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
  private final StreamBridge streamBridge;

  public CreateAccountProducerGatewayFilterFactory(
      StreamBridge streamBridge
  ) {
    this.streamBridge = streamBridge;
  }

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) -> {
      String id = GetHeadersOfRequest.getFullUser(exchange).id();

      this.streamBridge.send("createAccountProducer-out-0",  new CreateAccountDto(UUID.fromString(id)));

      return chain.filter(exchange);
    };
  }
}
