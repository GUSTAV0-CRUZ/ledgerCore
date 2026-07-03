package dev.cruzs.gustavo.service_bancary.user.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
  private static User user;

  @BeforeAll
  public static void setup() {
    user = User.create(
        UUID.randomUUID(),
        "Gustavo Cruz",
        LocalDate.parse("2006-01-01"),
        "gustavo.cruzs.dev@gmail.com",
        "123.456.789-74"
    );
  }

  @Test
  @DisplayName("Create user with success")
  void createUserSuccess() {
    UUID id = UUID.randomUUID();
    String name = "Gustavo Cruz";
    LocalDate dateOfBirth = LocalDate.parse("2001-01-01");
    String email = "gustavo.cruzs.dev@gmail.com";
    String cpf = "123.456.789-74";

    User result = User.create(
        id,
        name,
        dateOfBirth,
        email,
        cpf
    );

    assertEquals(id, result.getId());
    assertEquals(name, result.getName());
    assertEquals(dateOfBirth, result.getDateOfBirth());
    assertEquals(email, result.getEmail());
    assertEquals(cpf, result.getCpf());
  }

  @Test
  @DisplayName("Should return the error: (Name cannot be null)")
  void checkNameErrorCase1() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), null, user.getDateOfBirth(), user.getEmail(), user.getCpf())
    );

    assertEquals("Name cannot be null", result.getMessage());
  }

  @Test
  @DisplayName("Should not creat user, with error: (Name too short, size minimum 3!)")
  void checkNameErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), "Gu", user.getDateOfBirth(), user.getEmail(), user.getCpf())
    );

    assertEquals("Name too short, size minimum 3!", result.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (Date of birth cannot be null)")
  void checkDateOfBirthCase1() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), null, user.getEmail(), user.getCpf())
    );

    assertEquals("Date of birth cannot be null", result.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (Date too long!)")
  void checkDateOfBirthCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(
            user.getId(),
            user.getName(),
            LocalDate.now().minusYears(121),
            user.getEmail(),
            user.getCpf()
        )
    );

    assertEquals("Date too long!", result.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (User must be at least 16 years old!)")
  void checkDateOfBirthCase3() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(),
            user.getName(),
            LocalDate.now().minusYears(15),
            user.getEmail(),
            user.getCpf()
        )
    );

    assertEquals("User must be at least 16 years old!", result.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (Email cannot be empty or null!)")
  void checkEmailErrorCase1() {
    IllegalArgumentException result1 = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), null, user.getCpf())
    );
    IllegalArgumentException result2 = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), "", user.getCpf())
    );

    assertEquals("Email cannot be empty or null!", result1.getMessage());
    assertEquals("Email cannot be empty or null!", result2.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (Email is invalid!)")
  void checkEmailErrorCase2() {
    IllegalArgumentException result1 = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), "email@", user.getCpf())
    );

    assertEquals("Email is invalid!", result1.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (Cpf cannot be null or empty!)")
  void checkCpfErrorCase1() {
    IllegalArgumentException result1 = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), user.getEmail(), null)
    );
    IllegalArgumentException result2 = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), user.getEmail(), "")
    );

    assertEquals("Cpf cannot be null or empty!", result1.getMessage());
    assertEquals("Cpf cannot be null or empty!", result2.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (Cpf too short, size minimum 11!)")
  void checkCpfErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), user.getEmail(), "1234567890")
    );

    assertEquals("Cpf too short, size minimum 11!", result.getMessage());
  }
}
