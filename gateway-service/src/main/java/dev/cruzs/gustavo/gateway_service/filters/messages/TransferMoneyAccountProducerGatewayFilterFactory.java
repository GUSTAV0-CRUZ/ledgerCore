package dev.cruzs.gustavo.gateway_service.filters.messages;

import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.TransferMoneyAccountDto;
import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.TransferMoneyAccountRequestDto;
import dev.cruzs.gustavo.gateway_service.utils.GetHeadersOfRequest;
import dev.cruzs.gustavo.gateway_service.utils.dtos.FullUserHeadersRequestDto;
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
public class TransferMoneyAccountProducerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
  private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;
  private final Validator validator;
  private final StreamBridge streamBridge;

  public TransferMoneyAccountProducerGatewayFilterFactory(
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
        .setInClass(TransferMoneyAccountRequestDto.class)
        .setOutClass(String.class)
        .setRewriteFunction(
            TransferMoneyAccountRequestDto.class,
            String.class,
            (exchange, transferMoneyAccountRequestDto) -> {
              if (transferMoneyAccountRequestDto == null)
                throw new IllegalArgumentException("transferMoneyAccountRequestDto is null");

              Set<ConstraintViolation<TransferMoneyAccountRequestDto>> violations = validator.validate(
                  transferMoneyAccountRequestDto
              );

              if (!violations.isEmpty()) {
                String messagesError = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));

                throw new IllegalArgumentException(messagesError);
              }

              FullUserHeadersRequestDto fullUserHeadersRequestDto = GetHeadersOfRequest.getFullUser(exchange);
              TransferMoneyAccountDto transferMoneyAccountDto = new TransferMoneyAccountDto(
                  UUID.fromString(fullUserHeadersRequestDto.id()),
                  transferMoneyAccountRequestDto.amount(),
                  transferMoneyAccountRequestDto.recipientAccountId()
              );

              streamBridge.send("transferMoneyAccountProducer-out-0", transferMoneyAccountDto);

              return Mono.just("");
            }
        )
    );
  }
}
