package dev.serhats.hoodie.service.impl;

import dev.serhats.hoodie.domain.Role;
import dev.serhats.hoodie.domain.User;
import dev.serhats.hoodie.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final DefaultJWTService defaultJwtService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
    }

    public void registerUser(User user) {
        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role("ROLE_USER"));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // For now, we go simple
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setCreatedTime(Time.valueOf(LocalTime.now()));
        user.setUpdatedTime(Time.valueOf(LocalTime.now()));
    }

    public String getAccessToken(String username, String password, boolean rememberMe) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        //If credentials are wrong, then it will throw Exception
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return defaultJwtService.createToken(username, rememberMe);
    }

}
