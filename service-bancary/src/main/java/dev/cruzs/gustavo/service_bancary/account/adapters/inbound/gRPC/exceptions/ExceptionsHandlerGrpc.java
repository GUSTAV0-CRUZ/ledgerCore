package dev.cruzs.gustavo.service_bancary.account.adapters.inbound.gRPC.exceptions;

import dev.cruzs.gustavo.service_bancary.account.domain.exceptions.NotFoundAccountException;
import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class ExceptionsHandlerGrpc {
  @GrpcExceptionHandler(NotFoundAccountException.class)
  public Status notFoundAccountExceptionHandler(NotFoundAccountException exception) {
    return Status.NOT_FOUND
        .withDescription(exception.getMessage())
        .withCause(exception);
  }
}
