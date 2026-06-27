package dev.cruzs.gustavo.service_bancary.user.domain.exceptions;

public class DuplicateAttributeUserException extends RuntimeException {
  public DuplicateAttributeUserException(String message, Throwable reason) {
    super(message, reason);
  }
}
