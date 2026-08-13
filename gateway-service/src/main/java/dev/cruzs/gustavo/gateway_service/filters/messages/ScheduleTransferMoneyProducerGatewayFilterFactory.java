package dev.cruzs.gustavo.gateway_service.filters.messages;

import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.ScheduleTransferMoneyDto;
import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.ScheduleTransferMoneyRequestDto;
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
public class ScheduleTransferMoneyProducerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
  private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;
  private final Validator validator;
  private final StreamBridge streamBridge;

  public ScheduleTransferMoneyProducerGatewayFilterFactory(
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
        .setInClass(ScheduleTransferMoneyRequestDto.class)
        .setOutClass(String.class)
        .setRewriteFunction(
            ScheduleTransferMoneyRequestDto.class,
            String.class,
            (exchange, scheduleTransferMoneyRequestDto) -> {
              if (scheduleTransferMoneyRequestDto == null)
                throw new IllegalArgumentException("scheduleTransferMoneyRequestDto is null");

              Set<ConstraintViolation<ScheduleTransferMoneyRequestDto>> violations = validator.validate(
                  scheduleTransferMoneyRequestDto
              );

              if (!violations.isEmpty()) {
                String messagesError = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));

                throw new IllegalArgumentException(messagesError);
              }

              FullUserHeadersRequestDto fullUserHeadersRequestDto = GetHeadersOfRequest.getFullUser(exchange);
              ScheduleTransferMoneyDto transferMoneyAccountDto = new ScheduleTransferMoneyDto(
                  UUID.fromString(fullUserHeadersRequestDto.id()),
                  scheduleTransferMoneyRequestDto.amount(),
                  scheduleTransferMoneyRequestDto.recipientNumberAccount(),
                  scheduleTransferMoneyRequestDto.scheduledDate()
              );

              streamBridge.send("schedulingTransferProducer-out-0", transferMoneyAccountDto);

              return Mono.just("");
            }
        )
    );
  }
}
