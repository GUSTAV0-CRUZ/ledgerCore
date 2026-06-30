package dev.cruzs.gustavo.service_bancary.user.adapters.outbound.hasher;

import dev.cruzs.gustavo.service_bancary.user.application.ports.outbound.Hashing;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class HashingAdapter implements Hashing {
  @Override
  public String hash(String string) {
    return BCrypt.hashpw(string, BCrypt.gensalt());
  }

  @Override
  public boolean checkHash(String password, String passwordHash) {
    return BCrypt.checkpw(password, passwordHash);
  }
}
