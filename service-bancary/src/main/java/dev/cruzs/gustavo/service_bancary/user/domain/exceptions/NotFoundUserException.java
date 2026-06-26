package dev.cruzs.gustavo.service_bancary.user.domain.exceptions;

public class NotFoundUserException extends RuntimeException {
  public NotFoundUserException(String message) {
        super(message);
    }
}
