package dev.cruzs.gustavo.gateway_service.filters.messages;

import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.RegisterDataUserDto;
import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.RegisterDataUserRequestDto;
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
import java.util.stream.Collectors;

@Component
public class RegisterDataUserProducerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
  private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;
  private final Validator validator;
  private final StreamBridge streamBridge;

  public RegisterDataUserProducerGatewayFilterFactory(
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
    return modifyRequestBodyGatewayFilterFactory.apply(new ModifyRequestBodyGatewayFilterFactory.Config()
        .setInClass(RegisterDataUserRequestDto.class)
        .setOutClass(String.class)
        .setRewriteFunction(
            RegisterDataUserRequestDto.class,
            String.class,
            (exchange, registerDataUserRequestDto) -> {
              if (registerDataUserRequestDto == null)
                throw new IllegalArgumentException("Body is null");

              Set<ConstraintViolation<RegisterDataUserRequestDto>> violations = validator.validate(
                  registerDataUserRequestDto
              );

              if (!violations.isEmpty()) {
                String messagesError = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));

                throw new IllegalArgumentException(messagesError);
              }

              UserHeadersRequestDto userHeadersRequestDto = GetHeadersOfRequest.getFullUser(exchange);
              RegisterDataUserDto registerDataUserDto = new RegisterDataUserDto(
                  userHeadersRequestDto.id(),
                  registerDataUserRequestDto.name(),
                  registerDataUserRequestDto.dateOfBirth(),
                  userHeadersRequestDto.email(),
                  registerDataUserRequestDto.cpf()
              );

              streamBridge.send("registerDataUserProducer-out-0", registerDataUserDto);

              return Mono.just("");
            }
        )
    );
  }
}
