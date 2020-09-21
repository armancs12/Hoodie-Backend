package dev.serhats.hoodie.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
@PropertySource("classpath:jwt.properties")
public class DefaultJWTService {

    @Value("${jwt.secret}")
    private static String SECRET;

    @Value("${jwt.expirationTime}")
    private static String EXPIRATION_TIME;

    @Value("${jwt.rememberExpirationTime}")
    private static String REMEMBER_EXPIRATION_TIME;

    @Value("${auth.header}")
    private static String AUTH_HEADER;


    private static JWTVerifier VERIFIER;

    private static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET.getBytes());
    }

    public String createToken(String subject, boolean rememberMe) {
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(getIssuedAt())
                .withExpiresAt(getExpirationTime(rememberMe))
                .sign(getAlgorithm());
    }

    public JWTVerifier getVerifier() {
        if (VERIFIER == null) {
            VERIFIER = JWT.require(getAlgorithm()).build();
        }
        return VERIFIER;
    }

    private Date getIssuedAt() {
        return Time.valueOf(LocalTime.now());
    }

    private Date getExpirationTime(boolean rememberMe) {
        int[] times;
        if (rememberMe) {
            times = Arrays.stream(REMEMBER_EXPIRATION_TIME.split(":")).mapToInt(Integer::parseInt).toArray();
        } else {
            times = Arrays.stream(EXPIRATION_TIME.split(":")).mapToInt(Integer::parseInt).toArray();
        }
        return Time.valueOf(LocalTime.now()
                .plusHours(times[0] * 24) // Days
                .plusHours(times[1])
                .plusMinutes(times[2])
                .plusSeconds(times[3]));
    }

    public Optional<String> getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER);
        if (token != null && !token.isEmpty() && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}
