package dev.cruzs.gustavo.auth_service.domain;

import dev.cruzs.gustavo.auth_service.domain.valueObjects.Email;

import java.util.UUID;

public class User {
  private final UUID id;
  private String username;
  private Email email;
  private String password;

  private User(UUID id, String username, Email email, String password) {
    this.id = id;
    this.chekUsername(username);
    this.chekEmail(email);
    this.chekPassword(password);
  }

  public static User create(String username, Email email, String password) {
    return new User(
        UUID.randomUUID(),
        username,
        email,
        password
    );
  }

  public static User restore(UUID id, String username, Email email, String password) {
    return new User(
        id,
        username,
        email,
        password
    );
  }

  private void chekUsername(String username) {
    if (username == null || username.isEmpty())
      throw new IllegalArgumentException("username cannot be null or empty");

    if (username.length() < 3)
      throw new IllegalArgumentException("username must be at least 3 characters");

    this.username = username;
  }

  private void chekEmail(Email email) {
    if (email == null) throw new IllegalArgumentException("email cannot be null");

    this.email = email;
  }

  private void chekPassword(String password) {
    if (password == null || password.isEmpty())
      throw new IllegalArgumentException("password cannot be null or empty");

    if (password.length() < 8)
      throw new IllegalArgumentException("password must be at least 8 characters");

    this.password = password;
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public Email getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
