package dev.cruzs.gustavo.service_bancary.user.domain;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

public class User {
  private final static Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
  private final static LocalDate LIMITMAXDATEOFBIRTH = LocalDate.now().minusYears(120);
  private final static LocalDate LIMITMINDATEOFBIRTH = LocalDate.now().minusYears(16);

  private UUID id;
  private String name;
  private LocalDate dateOfBirth;
  private String email;
  private String cpf;

  private User(UUID id, String name, LocalDate dateOfBirth, String email, String cpf) {
    this.checkId(id);
    this.checkName(name);
    this.checkDateOfBirth(dateOfBirth);
    this.checkEmail(email);
    this.checkCpf(cpf);
  }

  public static User create(UUID id, String name, LocalDate dateOfBirth, String email, String cpf) {
    return new User(
        id,
        name,
        dateOfBirth,
        email,
        cpf
    );
  }

  public static User restore(UUID id, String name, LocalDate dateOfBirth, String email, String cpf) {
    return new User(
        id,
        name,
        dateOfBirth,
        email,
        cpf
    );
  }

  private void checkId(UUID id) {
    if (id == null) throw new IllegalArgumentException("ID cannot be null");

    this.id = id;
  }

  private void checkName(String newName) {
    if (newName == null) throw new IllegalArgumentException("Name cannot be null");
    if (newName.length() < 3) throw new IllegalArgumentException("Name too short, size minimum 3!");

    this.name = newName;
  }

  private void checkDateOfBirth(LocalDate newDateOfBirth) {
    if (newDateOfBirth == null) throw new IllegalArgumentException("Date of birth cannot be null");
    if (newDateOfBirth.isBefore(LIMITMAXDATEOFBIRTH)) throw new IllegalArgumentException("Date too long!");
    if (newDateOfBirth.isAfter(LIMITMINDATEOFBIRTH)) throw new IllegalArgumentException("User must be at least 16 years old!");

    this.dateOfBirth = newDateOfBirth;
  }

  private void checkEmail(String newEmail) {
    if (newEmail == null || newEmail.isEmpty()) throw new IllegalArgumentException("Email cannot be empty or null!");
    if (!EMAIL_REGEX.matcher(newEmail).matches()) throw  new IllegalArgumentException("Email is invalid!");

    this.email = newEmail;
  }

  private void checkCpf(String newCpf) {
    if (newCpf == null || newCpf.isEmpty()) throw new IllegalArgumentException("Cpf cannot be null or empty!");

    this.cpf = newCpf;
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

  public String getCpf() {
    return cpf;
  }
}
