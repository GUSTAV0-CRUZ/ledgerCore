package dev.cruzs.gustavo.gateway_service.filters.messages;

import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.CreateUserDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateUserProducerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
  private final StreamBridge streamBridge;
  private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;

  public CreateUserProducerGatewayFilterFactory(
      StreamBridge streamBridge,
      ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory
  ) {
    super(Object.class);
    this.streamBridge = streamBridge;
    this.modifyRequestBodyGatewayFilterFactory = modifyRequestBodyGatewayFilterFactory;
  }

  @Override
  public GatewayFilter apply(Object config) {
    return this.modifyRequestBodyGatewayFilterFactory.apply(new ModifyRequestBodyGatewayFilterFactory.Config()
      .setInClass(CreateUserDto.class)
      .setOutClass(String.class)
      .setRewriteFunction(
        CreateUserDto.class,
        String.class,
        (exchange, createUserDto) -> {
          if (createUserDto == null) throw new IllegalArgumentException("createUserDto is null");

          this.streamBridge.send("createUserProducer-out-0", createUserDto);
          exchange.getResponse().setStatusCode(HttpStatus.ACCEPTED);

          return Mono.just("");
        }
      )
    );
  }
}
