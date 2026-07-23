package dev.cruzs.gustavo.gateway_service.filters.global;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class JwtToHeaderFilter implements GlobalFilter, Ordered {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated)
        .map(Authentication::getPrincipal)
        .cast(Jwt.class)
        .map(jwt -> {
          String username = jwt.getSubject();
          Map<String, Object> userInfos = jwt.getClaimAsMap("userInfos");
          String userId = "";
          String email = "";

          if (userInfos != null) {
            userId = userInfos.get("id") != null ? userInfos.get("id").toString() : userId;
            email = userInfos.get("email") != null ? userInfos.get("email").toString() : email;
          }


          ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
              .header("X-User-Username", username != null ? username : "")
              .header("X-User-Id", userId)
              .header("X-User-Email", email)
              .build();

          return exchange.mutate().request(mutatedRequest).build();
        })
        .defaultIfEmpty(exchange)
        .flatMap(chain::filter);
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
