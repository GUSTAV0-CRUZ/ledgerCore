package dev.cruzs.gustavo.service_bancary.user.domain.valueObjects;

public class Cpf {
  private String cpf;

  public Cpf(String cpf) {
    this.checkCpf(cpf);
  }

  private void checkCpf(String cpf) {
    if (cpf == null || cpf.isEmpty()) throw new IllegalArgumentException("Cpf cannot be null or empty!");

    String cpfCharacterClear = cpf.replaceAll("[^0-9]", "");

    if (cpfCharacterClear.length() != 11) throw new IllegalArgumentException("Cpf must have exactly 11 digits!");
    if (!this.validate(cpfCharacterClear)) throw new IllegalArgumentException("Invalid CPF!");

    this.cpf = cpfCharacterClear;
  }

  private boolean validate(String cpf) {
    if (cpf.matches("(\\d)\\1{10}")) return false;

    char digitOne = this.calculateDigit(10, cpf);
    char digitTwo = this.calculateDigit(11, cpf);

    return (digitOne == cpf.charAt(9)) && (digitTwo == cpf.charAt(10));
  }

  private char calculateDigit(int weight, String cpf) {
    int sum = 0;
    int iterations = weight - 1;

    for (int i = 0; i < iterations; i++) {
      int num = Integer.parseInt(cpf.substring(i, i + 1));
      sum += (num * weight);
      weight--;
    }

    int remainder = 11 - (sum % 11);
    return (remainder == 10 || remainder == 11) ? '0' : (char) (remainder + '0');
  }

  public String getCpfInString() {
    return this.cpf;
  }
}
