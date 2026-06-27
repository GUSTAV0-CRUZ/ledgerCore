package dev.cruzs.gustavo.service_bancary.account.domain.exceptions;

public class DuplicateAttributeAccountException extends RuntimeException {
  public DuplicateAttributeAccountException(String message, Throwable reason) {
    super(message, reason);
  }
}
