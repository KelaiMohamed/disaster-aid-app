package ma.fstt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  // Must be the same value as auth-service's jwt.secret (JWT_SECRET env
  // var) - both services sign/verify with the same symmetric HS256 key.
  private final String secret;

  public JwtUtil(@Value("${jwt.secret}") String secret) {
    this.secret = secret;
  }

  public void validateToken(final String token) {
    try {
      Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

      // Check expiration
      Date expirationDate = claims.getExpiration();
      if (expirationDate != null && expirationDate.before(new Date())) {
        throw new ExpiredJwtException(null, null, "Token has expired", null);
      }
    } catch (ExpiredJwtException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Invalid token", e);
    }
  }

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
