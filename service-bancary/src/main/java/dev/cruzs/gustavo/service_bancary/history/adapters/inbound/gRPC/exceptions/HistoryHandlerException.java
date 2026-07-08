package dev.cruzs.gustavo.service_bancary.history.adapters.inbound.gRPC.exceptions;

import dev.cruzs.gustavo.service_bancary.history.domain.exceptions.NotFoundHistoryException;
import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class HistoryHandlerException {
  @GrpcExceptionHandler(NotFoundHistoryException.class)
  public Status notFoundHistoryException(NotFoundHistoryException error) {
    return Status.NOT_FOUND
      .withDescription(error.getMessage())
      .withCause(error.getCause());
  }
}
