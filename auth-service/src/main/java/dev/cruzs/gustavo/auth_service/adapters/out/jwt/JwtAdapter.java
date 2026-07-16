package dev.cruzs.gustavo.auth_service.adapters.out.jwt;

import dev.cruzs.gustavo.auth_service.application.ports.out.Jwt;
import dev.cruzs.gustavo.auth_service.application.ports.out.commands.GenerateJwtCommand;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtAdapter implements Jwt {
  @Value("${auth.jwt.secretKey}")
  private String secretKeyString;

  @Value("${auth.jwt.expiration-ms}")
  private long expirationMs;

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public String generate(GenerateJwtCommand generateJwtCommand) {
    Date now = new Date();
    Date datExpiration = new Date(now.getTime() + expirationMs);

    return Jwts.builder()
      .issuer("auth-service")
      .subject(generateJwtCommand.userName())
      .claim("userInfos", generateJwtCommand)
      .issuedAt(now)
      .expiration(datExpiration)
      .signWith(this.getSigningKey())
      .compact();
  }
}
