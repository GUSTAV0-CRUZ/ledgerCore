package dev.cruzs.gustavo.service_bancary.user.domain;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

public class User {
  private final static Pattern EMAIL_REGEX =Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

  private final UUID id;
  private String name;
  private LocalDate dateOfBirth;
  private String email;
  private String password;

  private User(UUID id, String name, LocalDate dateOfBirth, String email, String password) {
    this.id = id;
    this.changeName(name);
    this.changeDateOfBirth(dateOfBirth);
    this.changeEmail(email);
    this.changePassword(password);
  }

  public static User create(String name, LocalDate dateOfBirth, String email, String password) {
    return new User(
        UUID.randomUUID(),
        name,
        dateOfBirth,
        email,
        password
    );
  }

  public static User restore(UUID id, String name, LocalDate dateOfBirth, String email, String password) {
    return new User(
        id,
        name,
        dateOfBirth,
        email,
        password
    );
  }

  public void changeName(String newName) {
    if (newName.length() < 3) throw new IllegalArgumentException("Name too short, size minimum 3!");

    this.name = newName;
  }

  public void changeDateOfBirth(LocalDate newDateOfBirth) {
    LocalDate limitDateMax = LocalDate.now().minusYears(120);
    LocalDate limitDateMin = LocalDate.now().minusYears(16);
    if (newDateOfBirth.isBefore(limitDateMax)) throw new IllegalArgumentException("Date too long!");
    if (newDateOfBirth.isAfter(limitDateMin)) throw new IllegalArgumentException("User must be at least 16 years old!!");

    this.dateOfBirth = newDateOfBirth;
  }

  public void changeEmail(String newEmail) {
    if (newEmail.isEmpty()) throw new IllegalArgumentException("Email cannot be empty!");
    if (!EMAIL_REGEX.matcher(newEmail).matches()) throw  new IllegalArgumentException("Email is invalid!");

    this.email = newEmail;
  }

  public void changePassword(String newPassword) {
    if (newPassword.length() < 8) throw new IllegalArgumentException("Password too short, size minimum 8!");

    this.password = newPassword;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
