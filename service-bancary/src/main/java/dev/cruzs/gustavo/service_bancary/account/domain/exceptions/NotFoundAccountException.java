package dev.cruzs.gustavo.service_bancary.account.domain.exceptions;

public class NotFoundAccountException extends RuntimeException {
  public NotFoundAccountException(String message) {
        super(message);
    }
}
