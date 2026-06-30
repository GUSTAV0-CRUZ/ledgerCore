package dev.cruzs.gustavo.service_bancary.user.adapters.inbound.gRPC.exceptions;

import dev.cruzs.gustavo.service_bancary.user.domain.exceptions.NotFoundUserException;
import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class ExceptionHandlerUserGrpc {
  @GrpcExceptionHandler(NotFoundUserException.class)
  public Status notFoundUserExceptionHandler(NotFoundUserException exception) {
    return Status.NOT_FOUND
        .withDescription(exception.getMessage())
        .withCause(exception.getCause());
  }
}
