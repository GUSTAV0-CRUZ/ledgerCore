package dev.cruzs.gustavo.service_bancary.account.domain.enums;

public enum AccountTypeEnum {
  CURRENT("CURRENT"),
  SAVINGS("SAVINGS");

  private final String value;

  AccountTypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
