package dev.cruzs.gustavo.auth_service.domain.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
        super(message);
    }
}
