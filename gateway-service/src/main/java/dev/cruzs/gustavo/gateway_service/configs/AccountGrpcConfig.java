package dev.cruzs.gustavo.gateway_service.configs;

import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.ReactorAccountServiceGrpc;
import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.ReactorAccountServiceGrpc.ReactorAccountServiceStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountGrpcConfig {
  @Value("${grpc.account-service.host}")
  private String accountServiceHost;

  @Value("${grpc.account-service.port}")
  private int accountServicePort;

  @Bean
  public ManagedChannel managedChannelAccount() {
    return ManagedChannelBuilder
        .forAddress(accountServiceHost, accountServicePort)
        .usePlaintext()
        .build();
  }

  @Bean
  public ReactorAccountServiceStub reactorAccountServiceStub()  {
    return ReactorAccountServiceGrpc.newReactorStub(managedChannelAccount());
  }
}
