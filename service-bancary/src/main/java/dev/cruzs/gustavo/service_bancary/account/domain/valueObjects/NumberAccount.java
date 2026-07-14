package dev.cruzs.gustavo.service_bancary.account.domain.valueObjects;

import java.time.Instant;
import java.util.UUID;

public class NumberAccount {
  private String number;

  private NumberAccount(String number) {
    checkNumber(number);
  }

  public static NumberAccount create(UUID uuid) {
    return new NumberAccount(generateNumber(uuid));
  }

  public static NumberAccount restore(String number) {
    return new NumberAccount(number);
  }

  private void checkNumber(String number) {
    if (number == null) throw new IllegalArgumentException("uuid can't be null");

    this.number = number;
  }

  private static String generateNumber(UUID uuid) {
    long timestamp = Instant.now().toEpochMilli();
    String numericUuid = uuid.toString().replaceAll("[^0-9]", "");
    String numericUuidSafe = numericUuid.substring(0, 5);
    String baseNumber =  numericUuidSafe + timestamp;
    return baseNumber + "-" + calculateDigitFinal(baseNumber);
  }

  private static char calculateDigitFinal(String baseNumber) {
    int sum = 0, weight = 2;
    for (int i = baseNumber.length() - 1; i >= 0; i--) {
      sum += Character.getNumericValue(baseNumber.charAt(i)) * weight;
      weight = (weight >= 9) ? 2 : weight + 1;
    }
    int result = 11 - (sum % 11);
    return (result == 10) ? 'X' : (result == 11) ? '0' : Character.forDigit(result, 10);
  }

  public String getNumber() {
    return number;
  }
}
