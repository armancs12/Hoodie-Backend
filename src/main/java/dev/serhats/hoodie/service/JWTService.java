package dev.serhats.hoodie.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface JWTService {
    String createToken(String subject, boolean rememberMe);

    Optional<String> getTokenFromHeader(HttpServletRequest request);

    String verifyTokenAndGetSubject(String token);
}
