package dev.cruzs.gustavo.service_bancary.user.application.ports.outbound;

public interface Hashing {
  String hash(String string);
  boolean checkHash(String password, String passwordHash);
}
