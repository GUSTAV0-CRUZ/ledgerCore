package dev.cruzs.gustavo.gateway_service.exceptions;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.webflux.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Component
@Order(-2)
public class HandlerHttpExceptions implements ErrorWebExceptionHandler {
  private final Logger logger = LoggerFactory.getLogger(HandlerHttpExceptions.class);

  @Override
  @NullMarked
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    ServerHttpResponse response =  exchange.getResponse();

    if (response.isCommitted()) {
      return Mono.error(ex);
    }

    this.logger.warn(ex.getMessage(), ex);
    return switch (ex) {
      case IllegalArgumentException e -> this.sendError(response, e.getMessage(), HttpStatus.BAD_REQUEST);
      case NoSuchElementException e -> this.sendError(response, "Body is null", HttpStatus.BAD_REQUEST);
      case StatusRuntimeException e -> this.grpcException(response, e);
      case RuntimeException e -> this.sendError(response, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

      default -> this.sendError(response, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    };
  }

  private Mono<Void> sendError(ServerHttpResponse response, String message, HttpStatus status) {
    response.setStatusCode(status);
    return CustomError.responseJsonError(response, status, message);
  }

  private Mono<Void> grpcException(ServerHttpResponse response, StatusRuntimeException e) {
    String description = e.getStatus().getDescription();
    String message = description == null || description.isEmpty() ? "Description is empty or null" : description;

    HttpStatus status = switch (e.getStatus().getCode()) {
      case INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST;
      default -> HttpStatus.INTERNAL_SERVER_ERROR;
    };

    return sendError(response, message, status);
  }
}
