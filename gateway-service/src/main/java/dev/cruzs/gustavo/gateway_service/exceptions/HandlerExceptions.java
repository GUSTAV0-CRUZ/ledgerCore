package dev.cruzs.gustavo.gateway_service.exceptions;

import org.jspecify.annotations.NullMarked;
import org.springframework.boot.webflux.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class HandlerExceptions implements ErrorWebExceptionHandler {
  @Override
  @NullMarked
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    ServerHttpResponse response =  exchange.getResponse();

    return switch (ex) {
      case IllegalArgumentException e -> this.sendError(response, e.getMessage(), HttpStatus.BAD_REQUEST);
      default -> this.sendError(response, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    };
  }

  private Mono<Void> sendError(ServerHttpResponse response, String message, HttpStatus status) {
    response.setStatusCode(status);
    return CustomError.responseJsonError(response, status, message);
  }
}
