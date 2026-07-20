package dev.cruzs.gustavo.gateway_service.exceptions;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class CustomError {
  public static Mono<Void> responseJsonError(ServerHttpResponse response, HttpStatus status, String message) {
    String jsonResponse = String.format(
        "{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\"}",
        LocalDateTime.now(), status.value(), status.getReasonPhrase(), message
    );
    DataBuffer buffer = response.bufferFactory().wrap(jsonResponse.getBytes(StandardCharsets.UTF_8));
    return response.writeWith(Mono.just(buffer));
  }
}
