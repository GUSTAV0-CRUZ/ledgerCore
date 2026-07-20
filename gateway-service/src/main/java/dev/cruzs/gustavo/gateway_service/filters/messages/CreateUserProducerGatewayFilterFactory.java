package dev.cruzs.gustavo.gateway_service.filters.messages;

import dev.cruzs.gustavo.gateway_service.filters.messages.dtos.CreateUserDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CreateUserProducerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
  private final Logger logger = LoggerFactory.getLogger(CreateUserProducerGatewayFilterFactory.class);
  private final StreamBridge streamBridge;
  private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;
  private final Validator validator;

  public CreateUserProducerGatewayFilterFactory(
      StreamBridge streamBridge,
      ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory, Validator validator
  ) {
    super(Object.class);
    this.streamBridge = streamBridge;
    this.modifyRequestBodyGatewayFilterFactory = modifyRequestBodyGatewayFilterFactory;
    this.validator = validator;
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

          Set<ConstraintViolation<CreateUserDto>> violations = this.validator.validate(createUserDto);

          if (!violations.isEmpty()) {
            String messagesError = violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));

            throw new IllegalArgumentException(messagesError);
          }

          this.streamBridge.send("createUserProducer-out-0", createUserDto);
          this.logger.info("Try create user, with email: ({})", createUserDto.email());
          exchange.getResponse().setStatusCode(HttpStatus.ACCEPTED);

          return Mono.just("");
        }
      )
    );
  }
}
