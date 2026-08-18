package dev.cruzs.gustavo.gateway_service.configs;

import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.ReactorHistoryServiceGrpc;
import dev.cruzs.gustavo.gateway_service.filters.gRPC.generated.ReactorHistoryServiceGrpc.ReactorHistoryServiceStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoryGrpcConfig {
  @Value("${grpc.history-service.host}")
  private String historyServiceHost;

  @Value("${grpc.history-service.port}")
  private int historyServicePort;

  @Bean
  public ManagedChannel managedChannelHistory() {
    return ManagedChannelBuilder
        .forAddress(historyServiceHost, historyServicePort)
        .usePlaintext()
        .build();
  }

  @Bean
  public ReactorHistoryServiceStub reactorHistoryServiceStub()  {
    return ReactorHistoryServiceGrpc.newReactorStub(managedChannelHistory());
  }
}
