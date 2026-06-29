package dev.cruzs.gustavo.service_bancary.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
  private User user;

  @BeforeEach
  void setUp() {
    user = User.create(
        "Gustavo Cruz",
        LocalDate.parse("2001-01-01"),
        "gustavo.cruzs.dev@gmail.com",
        "12345678"
    );
  }

  @Test
  @DisplayName("Should update name of user with success")
  void changeNameSuccess() {
    this.user.changeName("Name Updated");
    assertEquals("Name Updated", user.getName());
  }

  @Test
  @DisplayName("Should not update name of user with error: (Name cannot be null)")
  void changeNameErrorCase1() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> this.user.changeName(null)
    );

    assertEquals("Name cannot be null", result.getMessage());
  }

  @Test
  @DisplayName("Should not update name of user with error: (Name too short, size minimum 3!)")
  void changeNameErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> this.user.changeName("Gu")
    );

    assertEquals("Name too short, size minimum 3!", result.getMessage());
  }

  @Test
  @DisplayName("Should update dateOfBirth of user with success")
  void changeDateOfBirthSuccess() {
    this.user.changeDateOfBirth(LocalDate.parse("2006-06-06"));

    assertEquals(LocalDate.parse("2006-06-06"), user.getDateOfBirth());
  }

  @Test
  @DisplayName("Should not update dateOfBirth of user with error: (Date of birth cannot be null)")
  void changeDateOfBirthCase1() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> this.user.changeDateOfBirth(null)
    );

    assertEquals("Date of birth cannot be null", result.getMessage());
  }

  @Test
  @DisplayName("Should not update dateOfBirth of user with error: (Date too long!)")
  void changeDateOfBirthCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> this.user.changeDateOfBirth(LocalDate.now().minusYears(121))
    );

    assertEquals("Date too long!", result.getMessage());
  }

  @Test
  @DisplayName("Should not update dateOfBirth of user with error: (User must be at least 16 years old!)")
  void changeDateOfBirthCase3() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> this.user.changeDateOfBirth(LocalDate.now().minusYears(15))
    );

    assertEquals("User must be at least 16 years old!", result.getMessage());
  }

  @Test
  @DisplayName("Should update email of user with success")
  void changeEmailSuccess() {
    this.user.changeEmail("emailUpdated@gmail.com");
    assertEquals("emailUpdated@gmail.com", user.getEmail());
  }

  @Test
  @DisplayName("Should not update email of user with error: (Email cannot be empty or null!)")
  void changeEmailErrorCase1() {
    IllegalArgumentException result1 = assertThrows(
        IllegalArgumentException.class,
        () -> this.user.changeEmail(null)
    );

    IllegalArgumentException result2 = assertThrows(
        IllegalArgumentException.class,
        () -> this.user.changeEmail("")
    );

    assertEquals("Email cannot be empty or null!", result1.getMessage());
    assertEquals("Email cannot be empty or null!", result2.getMessage());
  }

  @Test
  @DisplayName("Should not update email of user with error: (Email is invalid!)")
  void changeEmailErrorCase2() {
    IllegalArgumentException result1 = assertThrows(
        IllegalArgumentException.class,
        () -> this.user.changeEmail("email@")
    );

    assertEquals("Email is invalid!", result1.getMessage());
  }

  @Test
  @DisplayName("Should update password of user with success")
  void changePasswordSuccess() {
    this.user.changePassword("newPassword");
    assertEquals("newPassword", user.getPassword());
  }

  @Test
  @DisplayName("Should not update password of user with error: (Password cannot be null!)")
  void changePasswordErrorCase1() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> this.user.changePassword(null)
    );

    assertEquals("Password cannot be null!", result.getMessage());
  }

  @Test
  @DisplayName("Should not update password of user with error: (Password too short, size minimum 8!)")
  void changePasswordErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> this.user.changePassword("1234567")
    );

    assertEquals("Password too short, size minimum 8!", result.getMessage());
  }
}
