package dev.cruzs.gustavo.gateway_service.configs;

import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.AccountServiceGrpc;
import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.AccountServiceGrpc.AccountServiceStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountGrpcConfig {
  @Value("${grpc.gateway-service.host}")
  private String accountServiceHost;

  @Value("${grpc.gateway-service.port}")
  private int accountServicePort;

  @Bean
  public ManagedChannel managedChannel() {
    return ManagedChannelBuilder
        .forAddress(accountServiceHost, accountServicePort)
        .usePlaintext()
        .build();
  }

  @Bean
  public AccountServiceStub accountServiceBlockingStub()  {
    return AccountServiceGrpc.newStub(managedChannel());
  }
}
