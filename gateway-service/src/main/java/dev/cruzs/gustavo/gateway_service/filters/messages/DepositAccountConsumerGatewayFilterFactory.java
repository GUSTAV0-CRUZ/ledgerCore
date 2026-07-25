package dev.cruzs.gustavo.gateway_service.filters.messages;

import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.DepositAccountDto;
import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.DepositAccountRequestDto;
import dev.cruzs.gustavo.gateway_service.utils.GetHeadersOfRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DepositAccountConsumerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
  private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;
  private final Validator validator;
  private final StreamBridge streamBridge;

  public DepositAccountConsumerGatewayFilterFactory(
      ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory,
      Validator validator,
      StreamBridge streamBridge
  ) {
    this.modifyRequestBodyGatewayFilterFactory = modifyRequestBodyGatewayFilterFactory;
    this.validator = validator;
    this.streamBridge = streamBridge;
  }

  @Override
  public GatewayFilter apply(Object config) {
    return this.modifyRequestBodyGatewayFilterFactory.apply(new ModifyRequestBodyGatewayFilterFactory.Config()
        .setInClass(DepositAccountRequestDto.class)
        .setOutClass(String.class)
        .setRewriteFunction(
            DepositAccountRequestDto.class,
            String.class,
            (exchange, depositAccountRequestDto) -> {
              if (depositAccountRequestDto == null)
                throw new IllegalArgumentException("depositAccountRequestDto is null");

               Set<ConstraintViolation<DepositAccountRequestDto>> violations = this.validator.validate(
                   depositAccountRequestDto
               );

               if (!violations.isEmpty()) {
                 String messagesError = violations.stream()
                     .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                     .collect(Collectors.joining(", "));

                 throw new IllegalArgumentException(messagesError);
               }

               String id = GetHeadersOfRequest.getFullUser(exchange).id();

              DepositAccountDto depositAccountDto = new DepositAccountDto(
                  UUID.fromString(id),
                  depositAccountRequestDto.amount()
              );

              this.streamBridge.send("depositAccountConsumer-out-0", depositAccountDto);

              return Mono.just("");
            }
        )
    );
  }
}
