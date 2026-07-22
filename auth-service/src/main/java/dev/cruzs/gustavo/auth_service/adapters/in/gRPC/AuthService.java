package dev.cruzs.gustavo.auth_service.adapters.in.gRPC;

import dev.cruzs.gustavo.auth_service.adapters.inbound.gRPC.generated.AuthServiceGrpc.AuthServiceImplBase;
import dev.cruzs.gustavo.auth_service.adapters.inbound.gRPC.generated.AuthenticationRequest;
import dev.cruzs.gustavo.auth_service.adapters.inbound.gRPC.generated.AuthenticationResponse;
import dev.cruzs.gustavo.auth_service.application.ports.in.UserAuthenticationUseCase;
import dev.cruzs.gustavo.auth_service.application.ports.in.commands.UserAuthenticationCommand;
import dev.cruzs.gustavo.auth_service.domain.valueObjects.Email;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class AuthService extends AuthServiceImplBase {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final UserAuthenticationUseCase userAuthenticationUseCase;

  public AuthService(UserAuthenticationUseCase userAuthenticationUseCase) {
    this.userAuthenticationUseCase = userAuthenticationUseCase;
  }

  @Override
  public void authentication(AuthenticationRequest request, StreamObserver<AuthenticationResponse> responseObserver) {
    if (request.getPassword().isEmpty())
      throw new IllegalArgumentException("password required");

    String token = this.userAuthenticationUseCase.execute(
        new UserAuthenticationCommand(
            new Email(request.getEmail()),
            request.getPassword()
        )
    );

    AuthenticationResponse authenticationResponse = AuthenticationResponse.newBuilder()
        .setToken(token)
        .build();

    this.logger.info("authentication to email: {}", request.getEmail());
    responseObserver.onNext(authenticationResponse);
    responseObserver.onCompleted();
  }
}
