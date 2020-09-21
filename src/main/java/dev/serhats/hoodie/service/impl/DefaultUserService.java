package dev.serhats.hoodie.service.impl;

import dev.serhats.hoodie.domain.Role;
import dev.serhats.hoodie.domain.User;
import dev.serhats.hoodie.domain.repository.RoleRepository;
import dev.serhats.hoodie.domain.repository.UserRepository;
import dev.serhats.hoodie.dto.UserLogin;
import dev.serhats.hoodie.dto.UserRegister;
import dev.serhats.hoodie.dto.UserView;
import dev.serhats.hoodie.exception.NoEntityFoundException;
import dev.serhats.hoodie.service.AddressService;
import dev.serhats.hoodie.service.JWTService;
import dev.serhats.hoodie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AddressService addressService;
    private final JWTService jwtService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
    }

    public UserView registerUser(UserRegister userRegister) {
        User user = new User();

        Role userRole = roleRepository.findByName("ROLE_USER").orElse(new Role("ROLE_USER"));
        user.getRoles().add(userRole);

        user.setFirstName(userRegister.getFirstName());
        user.setLastName(userRegister.getLastName());
        user.setUsername(userRegister.getUsername());
        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        user.setProfilePictureUrl(userRegister.getProfilePictureUrl());
        user.setCurrentAddress(addressService.getFromGeolocation(userRegister.getGeolocation()));
        // For now, we go simple
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setDateCreated(new Date());

        return new UserView(user);
    }

    public String getAccessToken(UserLogin userLogin) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword());
        //If credentials are wrong, then it will throw Exception
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtService.createToken(userLogin.getUsername(), userLogin.isWillRemember());
    }

    @Override
    public UserView getUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoEntityFoundException("User"));
        return new UserView(user);
    }

}
