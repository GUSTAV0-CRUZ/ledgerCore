package dev.cruzs.gustavo.service_bancary.user.adapters.inbound.gRPC;

import dev.cruzs.gustavo.service_bancary.user.adapters.inbound.gRPC.generated.FindUserByIdRequest;
import dev.cruzs.gustavo.service_bancary.user.adapters.inbound.gRPC.generated.UserResponse;
import dev.cruzs.gustavo.service_bancary.user.adapters.inbound.gRPC.generated.UserServiceGrpc;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.FindUserByIdUseCase;
import dev.cruzs.gustavo.service_bancary.user.application.ports.inbound.commands.FindUserByIdCommand;
import dev.cruzs.gustavo.service_bancary.user.domain.User;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
  private final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final FindUserByIdUseCase findUserByIdUseCase;

  public UserService(FindUserByIdUseCase findUserByIdUseCase) {
    this.findUserByIdUseCase = findUserByIdUseCase;
  }

  @Override
  public void findUserById(FindUserByIdRequest request, StreamObserver<UserResponse> responseObserver) {
    User user = this.findUserByIdUseCase.execute(
        new FindUserByIdCommand(UUID.fromString(request.getId()))
    );

    UserResponse userResponse = UserResponse.newBuilder()
        .setId(user.getId().toString())
        .setName(user.getName())
        .setDateOfBirth(user.getDateOfBirth().toString())
        .setEmail(user.getEmail())
        .setCpf(user.getCpf().getCpfInString())
        .build();

    responseObserver.onNext(userResponse);
    logger.info("find user with id: {}", user.getId());
    responseObserver.onCompleted();
  }
}
