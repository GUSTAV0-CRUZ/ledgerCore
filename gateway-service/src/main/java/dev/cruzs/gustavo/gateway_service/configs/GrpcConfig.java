package dev.cruzs.gustavo.gateway_service.configs;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.cloud.gateway.config.GrpcSslConfigurer;
import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {
  @Bean
  public GrpcSslConfigurer grpcSslConfigurer(HttpClientProperties httpClientProperties, SslBundles sslBundles) {
    return new GrpcSslConfigurer(httpClientProperties.getSsl(), sslBundles) {
      @Override
      public ManagedChannel configureSsl(NettyChannelBuilder NettyChannelBuilder) {
        return NettyChannelBuilder.usePlaintext().build();
      }
    };
  }
}
