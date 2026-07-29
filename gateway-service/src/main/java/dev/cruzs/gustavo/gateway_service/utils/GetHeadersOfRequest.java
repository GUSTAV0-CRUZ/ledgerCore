package dev.cruzs.gustavo.gateway_service.utils;

import dev.cruzs.gustavo.gateway_service.utils.dtos.FullUserHeadersRequestDto;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

public class GetHeadersOfRequest {
  public static FullUserHeadersRequestDto getFullUser(ServerWebExchange exchange) {
    HttpHeaders headers = exchange.getRequest().getHeaders();
    String username = headers.get("X-User-Username") != null ? headers.get("X-User-Username").getFirst() : "";
    String id = headers.get("X-User-Id") != null ? headers.get("X-User-Id").getFirst() : "";
    String email = headers.get("X-User-Id") != null  ? headers.get("X-User-Email").getFirst() : "";

    return new FullUserHeadersRequestDto(id, username, email);
  }
}
