package dev.cruzs.gustavo.service_bancary.domain.enums;

public enum AccountStatusEnum {
  ACTIVE("ACTIVE"),
  BLOCKED("BLOCKED"),
  SUSPENDED("SUSPENDED"),
  CLOSED("CLOSED");

  private final String value;

  AccountStatusEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
