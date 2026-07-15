package dev.cruzs.gustavo.auth_service.domain.valueObjects;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
  private static final Pattern PATTERN = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
  private final String value;

  public Email(String email) {
    this.value = this.checkEmail(email);
  }

  private String checkEmail(String email) {
    if (email == null || email.isEmpty())
      throw new IllegalArgumentException("Email is null or empty");

    String emailClean = this.normalizeEmail(email);

    this.validateEmail(emailClean);
    return emailClean;
  }

  private String normalizeEmail(String email) {
    return email.trim().toLowerCase();
  }

  private void validateEmail(String email) {
    if (!PATTERN.matcher(email).matches())
      throw new IllegalArgumentException("Invalid email");
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Email email = (Email) o;
    return Objects.equals(value, email.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  @Override
  public String toString() {
    return this.value;
  }
}
