package dev.cruzs.gustavo.auth_service.adapters.out.hash;

import dev.cruzs.gustavo.auth_service.application.ports.out.Hash;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HashAdapter implements Hash {
  private final PasswordEncoder passwordEncoder;

  public HashAdapter(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String generate(String password) {
    return passwordEncoder.encode(password);
  }

  @Override
  public boolean validate(String password, String hashPassword) {
    return passwordEncoder.matches(password, hashPassword);
  }
}
