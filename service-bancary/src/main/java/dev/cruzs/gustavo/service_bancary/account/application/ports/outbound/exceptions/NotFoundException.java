package dev.cruzs.gustavo.service_bancary.account.application.ports.outbound.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
        super(message);
    }
}
