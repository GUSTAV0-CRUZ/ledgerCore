package dev.cruzs.gustavo.auth_service.adapters.in.gRPC.exceptions;

import dev.cruzs.gustavo.auth_service.domain.exceptions.NotFoundException;
import io.grpc.Status;
import io.grpc.StatusException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class ExceptionsHandlerGrpc implements GrpcExceptionHandler {
  private final Logger logger = LoggerFactory.getLogger(ExceptionsHandlerGrpc.class);

  @Override
  public @Nullable StatusException handleException(@NonNull Throwable exception) {
    if (exception instanceof IllegalArgumentException)
      return this.patronizeExceptions(Status.INVALID_ARGUMENT, exception);

    if (exception instanceof NotFoundException)
      return this.patronizeExceptions(Status.NOT_FOUND, exception);

    this.logger.info(exception.getMessage(), exception);
    return Status.INTERNAL
        .withDescription("Unknown Error.")
        .asException();
  }

  private StatusException patronizeExceptions(Status typeStatus, Throwable exception) {
    return typeStatus
        .withCause(exception)
        .withDescription(exception.getMessage())
        .asException();
  }
}
