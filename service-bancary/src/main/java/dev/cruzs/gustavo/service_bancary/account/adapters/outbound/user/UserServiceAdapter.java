package dev.cruzs.gustavo.service_bancary.account.adapters.outbound.user;

import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.UserService;
import dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.dtos.UserResponseDto;
import dev.cruzs.gustavo.service_bancary.user.adapters.inbound.gRPC.generated.FindUserByIdRequest;
import dev.cruzs.gustavo.service_bancary.user.adapters.inbound.gRPC.generated.UserResponse;
import dev.cruzs.gustavo.service_bancary.user.adapters.inbound.gRPC.generated.UserServiceGrpc.UserServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserServiceAdapter implements UserService {
  @GrpcClient("user-service")
  private UserServiceBlockingStub userServiceBlockingStub;

  @Override
  public UserResponseDto findById(UUID userId) {
    FindUserByIdRequest findUserByIdRequest = FindUserByIdRequest.newBuilder()
        .setId(userId.toString())
        .build();

    UserResponse userResponse = userServiceBlockingStub.findUserById(findUserByIdRequest);
    
    return new UserResponseDto(
        UUID.fromString(userResponse.getId()),
        userResponse.getEmail(),
        userResponse.getName()
    );
  }
}
