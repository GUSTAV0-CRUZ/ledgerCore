package dev.cruzs.gustavo.service_bancary.schedulingWork.domain.enums;

public enum SchedulingEnum {
  PENDING("PENDING"),
  PROCESSED("PROCESSED");

  private final String value;

  SchedulingEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
