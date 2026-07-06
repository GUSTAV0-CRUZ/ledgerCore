package dev.cruzs.gustavo.service_bancary.user.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

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
        "584.247.280-50"
    );
  }

  @Test
  @DisplayName("Create user with success")
  void createUserSuccess() {
    UUID id = UUID.randomUUID();
    String name = "Gustavo Cruz";
    LocalDate dateOfBirth = LocalDate.parse("2001-01-01");
    String email = "gustavo.cruzs.dev@gmail.com";
    String cpf = "584.247.280-50";

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
    assertEquals(cpf.replaceAll("[^0-9]", ""), result.getCpf().getCpfInString());
  }

  @Test
  @DisplayName("Should return the error: (Name cannot be null)")
  void checkNameErrorCase1() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), null, user.getDateOfBirth(), user.getEmail(), user.getCpf().getCpfInString())
    );

    assertEquals("Name cannot be null", result.getMessage());
  }

  @Test
  @DisplayName("Should not creat user, with error: (Name too short, size minimum 3!)")
  void checkNameErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), "Gu", user.getDateOfBirth(), user.getEmail(), user.getCpf().getCpfInString())
    );

    assertEquals("Name too short, size minimum 3!", result.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (Date of birth cannot be null)")
  void checkDateOfBirthCase1() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), null, user.getEmail(), user.getCpf().getCpfInString())
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
            user.getCpf().getCpfInString()
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
            user.getCpf().getCpfInString()
        )
    );

    assertEquals("User must be at least 16 years old!", result.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (Email cannot be empty or null!)")
  void checkEmailErrorCase1() {
    IllegalArgumentException result1 = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), null, user.getCpf().getCpfInString())
    );
    IllegalArgumentException result2 = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), "", user.getCpf().getCpfInString())
    );

    assertEquals("Email cannot be empty or null!", result1.getMessage());
    assertEquals("Email cannot be empty or null!", result2.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (Email is invalid!)")
  void checkEmailErrorCase2() {
    IllegalArgumentException result1 = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), "email@", user.getCpf().getCpfInString())
    );

    assertEquals("Email is invalid!", result1.getMessage());
  }

  @ParameterizedTest
  @NullSource
  @EmptySource
  @DisplayName("Should return the error: (Cpf cannot be null or empty!)")
  void checkCpfErrorCase1(String invalidCpf) {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), user.getEmail(), invalidCpf)
    );

    assertEquals("Cpf cannot be null or empty!", result.getMessage());
  }

  @Test
  @DisplayName("Should return the error: (Cpf must have exactly 11 digits!)")
  void checkCpfErrorCase2() {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), user.getEmail(), "1234567890")
    );

    assertEquals("Cpf must have exactly 11 digits!", result.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"12345678901", "11111111111"})
  @DisplayName("Should return the error: (Invalid CPF!)")
  void checkCpfErrorCase3(String invalidCpf) {
    IllegalArgumentException result = assertThrows(
        IllegalArgumentException.class,
        () -> User.create(user.getId(), user.getName(), user.getDateOfBirth(), user.getEmail(), invalidCpf)
    );

    assertEquals("Invalid CPF!", result.getMessage());
  }
}
