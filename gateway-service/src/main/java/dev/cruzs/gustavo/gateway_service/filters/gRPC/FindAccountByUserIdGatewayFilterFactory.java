package dev.cruzs.gustavo.gateway_service.filters.gRPC;
import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.AccountResponse;
import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.AccountServiceGrpc.AccountServiceStub;
import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.FindAccountByUserIdRequest;
import dev.cruzs.gustavo.gateway_service.utils.ConversionsOfTypes;
import dev.cruzs.gustavo.gateway_service.utils.GetHeadersOfRequest;
import dev.cruzs.gustavo.gateway_service.utils.dtos.FullUserHeadersRequestDto;
import io.grpc.stub.StreamObserver;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class FindAccountByUserIdGatewayFilterFactory extends AbstractGatewayFilterFactory<FindAccountByUserIdGatewayFilterFactory.Config> {
  private final AccountServiceStub accountServiceStub;

  public FindAccountByUserIdGatewayFilterFactory(AccountServiceStub accountServiceStub) {
    super(Config.class);
    this.accountServiceStub = accountServiceStub;
  }

  public static class Config {}

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      FullUserHeadersRequestDto fullUserHeadersRequestDto = GetHeadersOfRequest.getFullUser(exchange);
      FindAccountByUserIdRequest findAccountByUserIdRequest = FindAccountByUserIdRequest.newBuilder()
          .setUserId(fullUserHeadersRequestDto.id())
          .build();

      return this.findAccountByUserIdMono(findAccountByUserIdRequest)
        .flatMap(accountResponse -> this.sendResponse(exchange, accountResponse));
    };
  }

  private Mono<AccountResponse> findAccountByUserIdMono(FindAccountByUserIdRequest findAccountByUserIdRequest) {
    return Mono.<AccountResponse>create(monoSink -> {
      accountServiceStub.findAccountByUserId(
        findAccountByUserIdRequest,
        new StreamObserver<>() {
          @Override
          public void onNext(AccountResponse value) {
            monoSink.success(value);
          }

          @Override
          public void onError(Throwable t) {
            monoSink.error(t);
          }

          @Override
          public void onCompleted() {}
        }
      );
    });
  }

  private Mono<Void> sendResponse(ServerWebExchange exchange, AccountResponse accountResponse) {
    byte[] data = ConversionsOfTypes.protobufToJson(accountResponse);
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.OK);
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

    DataBuffer buffer = response.bufferFactory().wrap(data);

    return response.writeWith(Mono.just(buffer));
  }
}
