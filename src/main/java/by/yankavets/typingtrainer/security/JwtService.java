package by.yankavets.typingtrainer.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String generateToken(UserDetails userDetails);
    String extractUsername(String token);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    boolean isValidToken(String token, UserDetails userDetails);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

}
