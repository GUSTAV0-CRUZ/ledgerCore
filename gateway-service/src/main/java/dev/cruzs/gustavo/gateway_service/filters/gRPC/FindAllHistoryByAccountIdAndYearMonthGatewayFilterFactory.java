package dev.cruzs.gustavo.gateway_service.filters.gRPC;
import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.*;
import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.ReactorHistoryServiceGrpc.ReactorHistoryServiceStub;
import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.ReactorAccountServiceGrpc.ReactorAccountServiceStub;
import dev.cruzs.gustavo.gateway_service.utils.ConversionsOfTypes;
import dev.cruzs.gustavo.gateway_service.utils.GetHeadersOfRequest;
import dev.cruzs.gustavo.gateway_service.utils.dtos.FullUserHeadersRequestDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Map;

@Component
public class FindAllHistoryByAccountIdAndYearMonthGatewayFilterFactory extends AbstractGatewayFilterFactory<FindAllHistoryByAccountIdAndYearMonthGatewayFilterFactory.Config> {
  private final ReactorHistoryServiceStub reactorHistoryServiceStub;
  private final ReactorAccountServiceStub reactorAccountServiceStub;

  public FindAllHistoryByAccountIdAndYearMonthGatewayFilterFactory(
      ReactorHistoryServiceStub reactorHistoryServiceStub,
      ReactorAccountServiceStub reactorAccountServiceStub) {
    super(Config.class);
    this.reactorHistoryServiceStub = reactorHistoryServiceStub;
    this.reactorAccountServiceStub = reactorAccountServiceStub;
  }

  public static class Config {}

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      Map<String, String> uriVariables = ServerWebExchangeUtils.getUriTemplateVariables(exchange);

      YearMonth yearMonth = YearMonth.now(ZoneOffset.UTC);

      String year = uriVariables.getOrDefault("year", String.valueOf(yearMonth.getYear()));
      String month = uriVariables.getOrDefault("month", String.valueOf(yearMonth.getMonthValue()));

      FullUserHeadersRequestDto fullUserHeadersRequestDto = GetHeadersOfRequest.getFullUser(exchange);

      FindAccountByUserIdRequest findAccountByUserIdRequest = FindAccountByUserIdRequest.newBuilder()
          .setUserId(fullUserHeadersRequestDto.id())
          .build();

      return reactorAccountServiceStub.findAccountByUserId(findAccountByUserIdRequest)
          .flatMap(accountResponse -> {
            FindAllByAccountIdAndYearMonthRequest findAllByAccountIdAndYearMonthRequest =
                FindAllByAccountIdAndYearMonthRequest.newBuilder()
                  .setAccountId(accountResponse.getId())
                  .setYearMonth(year + "-" + (month.length() > 1 ? month : "0" + month))
                  .build();

            return reactorHistoryServiceStub.findAllByAccountIdAndYearMonth(findAllByAccountIdAndYearMonthRequest);
          })
          .flatMap(historyResponse -> sendResponse(exchange, historyResponse))
          .onErrorResume(throwable -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return response.setComplete();
          });
    };
  }

  private Mono<Void> sendResponse(ServerWebExchange exchange, ListHistoriesResponse historyResponse) {
    byte[] data = ConversionsOfTypes.protobufToJson(historyResponse);
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.OK);
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

    DataBuffer buffer = response.bufferFactory().wrap(data);

    return response.writeWith(Mono.just(buffer));
  }
}
