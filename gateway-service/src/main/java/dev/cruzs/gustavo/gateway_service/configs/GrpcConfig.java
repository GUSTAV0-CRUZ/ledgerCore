package dev.cruzs.gustavo.gateway_service.configs;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.cloud.gateway.config.GrpcSslConfigurer;
import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class GrpcConfig {
  private final Map<String, ManagedChannel> channelCache = new ConcurrentHashMap<>();

  @Bean
  public GrpcSslConfigurer grpcSslConfigurer(HttpClientProperties httpClientProperties, SslBundles sslBundles) {
    return new GrpcSslConfigurer(httpClientProperties.getSsl(), sslBundles) {
      @Override
      public ManagedChannel configureSsl(NettyChannelBuilder nettyChannelBuilder) {
        nettyChannelBuilder.usePlaintext();

        ManagedChannel newManagedChannel = nettyChannelBuilder.build();

        String target = newManagedChannel.authority();

        ManagedChannel existingChannel =  channelCache.putIfAbsent(target, newManagedChannel);

        if (existingChannel != null) {
          newManagedChannel.shutdownNow();
          return existingChannel;
        }

        return newManagedChannel;
      }
    };
  }
  
  @PreDestroy
  public void shutdownNowAllChannelCache() {
    channelCache.values().forEach(ManagedChannel::shutdownNow);
  }
}
