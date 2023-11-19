package mskory.bookstore.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final Encryptor encryptor;
    @Value("${project.name}")
    private String issuer;
    @Value("${jwt.token.expiration.time}")
    private Long expirationTime;

    public JwtUtil(@Value("${jwt.token.secret}") String secret, Encryptor encryptor) {
        this.algorithm = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(algorithm).build();
        this.encryptor = encryptor;
    }

    public String createToken(String subject) {
        ZonedDateTime currentZonedDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
        return JWT.create()
                .withSubject(encryptor.encrypt(subject))
                .withIssuedAt(currentZonedDateTime.toInstant())
                .withExpiresAt(currentZonedDateTime.plusMinutes(expirationTime).toInstant())
                .sign(algorithm);
    }

    public boolean isValid(String token) {
        return verifier.verify(token)
                .getExpiresAt()
                .after(new Date());
    }

    public String getSubject(String token) {
        return encryptor.decrypt(JWT.decode(token).getSubject());
    }
}
