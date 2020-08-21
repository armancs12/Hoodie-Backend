package dev.serhats.hoodie.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.serhats.hoodie.service.JWTService;
import dev.serhats.hoodie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Filter
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        jwtService.getTokenFromHeader(httpServletRequest)
                .ifPresent(token -> {
                    //Throws exception if token is couldn't be verified
                    DecodedJWT decodedJWT = jwtService.getVerifier().verify(token);
                    String subject = decodedJWT.getSubject();
                    UserDetails userDetails = userService.loadUserByUsername(subject);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails.getUsername(),
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}