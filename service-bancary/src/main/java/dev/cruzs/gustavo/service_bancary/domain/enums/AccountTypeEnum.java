package dev.cruzs.gustavo.service_bancary.application.domain.enums;

public enum TypeAccountEnum {
  CURRENT("CURRENT"),
  SAVINGS("SAVINGS");

  private final String value;

  TypeAccountEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
