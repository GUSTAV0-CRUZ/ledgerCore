package dev.cruzs.gustavo.auth_service.domain;

import dev.cruzs.gustavo.auth_service.domain.valueObjects.Email;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
  private static User user;

  @Test
  @DisplayName("should create user with success")
  void createUserSuccess() {
    String username = "Gustavo Cruz";
    Email email = new Email("gustavo.cruzs.dev@gmail.com");
    String password = "password123";

    User user = User.create(username, email, password);

    assertNotNull(user);
    assertNotNull(user.getId());
    assertEquals(username, user.getUsername());
    assertEquals(email, user.getEmail());
    assertEquals(password, user.getPassword());
  }

  @BeforeAll
  static void setUp() {
    UserTest.user = User.create(
        "Gustavo Cruz",
        new Email("gustavo.cruzs.dev@gmail.com"),
        "password123"
    );
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("should return error: username cannot be null or empty")
  public void chekUsernameErrorCase1(String invalidUsername) {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(
          invalidUsername,
          user.getEmail(),
          user.getPassword()
        )
    );

    assertEquals("username cannot be null or empty", result.getMessage());
  }

  @Test
  @DisplayName("should return error: username must be at least 3 characters")
  void chekUsernameErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(
            "Gu",
            user.getEmail(),
            user.getPassword()
        )
    );

    assertEquals("username must be at least 3 characters", result.getMessage());
  }

  @Test
  @DisplayName("should return error: email cannot be null")
  void chekEmailError() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(
            user.getUsername(),
            null,
            user.getPassword()
        )
    );

    assertEquals("email cannot be null", result.getMessage());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("should return error: password cannot be null or empty")
  void chekPasswordErrorCase1(String invalidPassword) {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(
            user.getUsername(),
            user.getEmail(),
            invalidPassword
        )
    );

    assertEquals("password cannot be null or empty", result.getMessage());
  }

  @Test
  @DisplayName("should return error: password must be at least 8 characters")
  void chekPasswordErrorCase2() {
    var result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(
            user.getUsername(),
            user.getEmail(),
            "1234567"
        )
    );

    assertEquals("password must be at least 8 characters", result.getMessage());
  }
}