package dev.cruzs.gustavo.gateway_service.filters.messages;

import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.WithdrawAccountDto;
import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.WithdrawAccountRequestDto;
import dev.cruzs.gustavo.gateway_service.utils.GetHeadersOfRequest;
import dev.cruzs.gustavo.gateway_service.utils.dtos.UserHeadersRequestDto;
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
public class WithdrawAccountProducerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
  private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;
  private final Validator validator;
  private final StreamBridge streamBridge;

  public WithdrawAccountProducerGatewayFilterFactory(
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
        .setInClass(WithdrawAccountRequestDto.class)
        .setOutClass(String.class)
        .setRewriteFunction(
            WithdrawAccountRequestDto.class,
            String.class,
            (exchange, withdrawAccountRequestDto) -> {
              if (withdrawAccountRequestDto == null)
                throw new IllegalArgumentException("withdrawAccountRequestDto is null");

              Set<ConstraintViolation<WithdrawAccountRequestDto>> violations = validator.validate(
                  withdrawAccountRequestDto
              );

              if (!violations.isEmpty()) {
                String messagesError = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));

                throw new IllegalArgumentException(messagesError);
              }

              UserHeadersRequestDto userHeadersRequestDto = GetHeadersOfRequest.getFullUser(exchange);
              WithdrawAccountDto withdrawAccountDto = new WithdrawAccountDto(
                  UUID.fromString(userHeadersRequestDto.id()),
                  withdrawAccountRequestDto.amount()
              );

              streamBridge.send("withdrawAccountProducer-out-0", withdrawAccountDto);

              return Mono.just("");
            }
        )
    );
  }
}
