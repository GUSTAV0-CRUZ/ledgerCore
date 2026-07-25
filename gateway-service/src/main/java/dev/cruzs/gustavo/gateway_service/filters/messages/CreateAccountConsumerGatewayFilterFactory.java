package dev.cruzs.gustavo.gateway_service.filters.messages;

import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.CreateAccountDto;
import dev.cruzs.gustavo.gateway_service.utils.GetHeadersOfRequest;
import dev.cruzs.gustavo.gateway_service.utils.dtos.UserHeadersRequestDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountConsumerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
  private final StreamBridge streamBridge;

  public CreateAccountConsumerGatewayFilterFactory(
      StreamBridge streamBridge
  ) {
    this.streamBridge = streamBridge;
  }

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) -> {
      String id = GetHeadersOfRequest.getFullUser(exchange).id();

      this.streamBridge.send("createAccountConsumer-out-0",  new CreateAccountDto(id));

      return chain.filter(exchange);
    };
  }
}
