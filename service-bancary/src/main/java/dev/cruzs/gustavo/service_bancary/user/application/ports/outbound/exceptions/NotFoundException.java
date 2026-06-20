package dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
        super(message);
    }
}
