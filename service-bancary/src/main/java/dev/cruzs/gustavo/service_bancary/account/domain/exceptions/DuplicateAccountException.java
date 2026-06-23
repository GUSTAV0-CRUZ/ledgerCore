package dev.cruzs.gustavo.service_bancary.account.domain.exceptions;

public class DuplicateAccountException extends RuntimeException {
  public DuplicateAccountException(String message, Throwable reason) {
    super(message, reason);
  }
}
