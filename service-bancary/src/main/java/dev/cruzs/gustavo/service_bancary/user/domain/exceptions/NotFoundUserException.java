package dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.exceptions;

public class NotFoundUserException extends RuntimeException {
  public NotFoundUserException(String message) {
        super(message);
    }
}
