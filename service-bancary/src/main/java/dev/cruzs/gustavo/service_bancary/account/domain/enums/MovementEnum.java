package dev.cruzs.gustavo.service_bancary.account.domain.enums;

public enum MovementEnum {
  TRANSFER("TRANSFER"),
  WITHDRAW("WITHDRAW"),
  DEPOSIT("DEPOSIT");

  private final String value;

  MovementEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
