package dev.cruzs.gustavo.auth_service.application.ports.out;

public interface Hash {
  String generate(String password);
  boolean validate(String password, String hashPassword);
}
